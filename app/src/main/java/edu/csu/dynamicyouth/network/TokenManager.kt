package edu.csu.dynamicyouth.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        //TODO
    }

    fun getAccessToken(): Flow<String?> {
        //TODO
        return flowOf(null)
    }

    fun getRefreshToken(): String? {
        //TODO
        return null
    }
}