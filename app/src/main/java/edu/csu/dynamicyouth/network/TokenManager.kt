package edu.csu.dynamicyouth.network

import android.content.Context
import android.content.Intent
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.csu.dynamicyouth.AuthActivity
import edu.csu.dynamicyouth.api.UserApi
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Token管理器。该管理器支持token管理自动化，当token过期时可以获取新的token。
 *
 * @param pureUserApi 纯净的UserApi，用于获取新的token。注意，**该Api必须是pure的，即不可添加[AuthInterceptor]**。
 */
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pureUserApi: UserApi
) {

    private val authResultChannel = Channel<String?>(Channel.UNLIMITED)

    private fun saveToken(token: String) {
        val secureSharedPrefs = getSecureSharedPreferences(context)
        with(secureSharedPrefs.edit()) {
            putString("token", token)
            apply()
        }
    }

    /**
     * 获取已经存在的token，如果不存在，则返回null。
     */
    fun getExistingToken(): String? {
        val secureSharedPrefs = getSecureSharedPreferences(context)
        return secureSharedPrefs.getString("token", null)
    }

    /**
     * 获取新的token。如果获取失败，则返回null。
     */
    suspend fun getNewToken(): String? {
        val intent = Intent(context, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)

        val token = withTimeoutOrNull(60000L) { //60s超时
            authResultChannel.receive() //挂起，直到AuthActivity返回
        }

        return token
    }

    private fun getSecureSharedPreferences(context: Context): EncryptedSharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // 2. 创建 EncryptedSharedPreferences 实例
        val sharedPrefs = EncryptedSharedPreferences.create(
            "dy_secure_auth_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // 用于加密 Key (键名)
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // 用于加密 Value (键值，即你的 Token)
        ) as EncryptedSharedPreferences

        return sharedPrefs
    }

    /**
     * [AuthActivity]应当调用该方法返回token。
     */
    fun sendTokenResult(token: String?) {
        authResultChannel.trySend(token)
    }

}