package edu.csu.dynamicyouth.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadFileApi {

    /**
     * [handleAvatarFileUpload](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/362990202e0)
     */
    @POST("upload/avatar")
    @Multipart
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): ApiResponse<String>
}