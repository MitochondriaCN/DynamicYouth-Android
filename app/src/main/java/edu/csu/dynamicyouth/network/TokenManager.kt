package edu.csu.dynamicyouth.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.csu.dynamicyouth.api.UserApi
import jakarta.inject.Inject

/**
 * Token管理器。该管理器支持token管理自动化，当token过期时可以获取新的token。
 *
 * @param pureUserApi 纯净的UserApi，用于获取新的token。注意，**该Api必须是pure的，即不可添加`AuthInterceptor`**。
 */
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pureUserApi: UserApi
) {

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        TODO()
    }

    fun getExistingToken(): String? {
        TODO()
    }

    suspend fun getNewToken(): String? {
        TODO("这应当支持在任何界面直接唤起一个登录窗口，这时函数挂起，待登录后再返回")
    }

    fun getRefreshToken(): String? {
        TODO()
    }
}