package edu.csu.dynamicyouth.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.csu.dynamicyouth.api.UserApi
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import edu.csu.dynamicyouth.BuildConfig
import edu.csu.dynamicyouth.api.CollegeApi
import edu.csu.dynamicyouth.api.RecordApi
import edu.csu.dynamicyouth.api.UploadFileApi
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(
            context,
            provideUserApi(provideRetrofit(getPureOkHttpClient(), provideJson()))
        )
    }

    @Provides
    @Singleton
    fun provideAuthOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager, provideJson()))
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
            .build()
    }

    fun getPureOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            //kotlinx serialization
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecordApi(retrofit: Retrofit): RecordApi {
        return retrofit.create(RecordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUploadFileApi(retrofit: Retrofit): UploadFileApi {
        return retrofit.create(UploadFileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCollegeApi(retrofit: Retrofit): CollegeApi {
        return retrofit.create(CollegeApi::class.java)
    }
}
