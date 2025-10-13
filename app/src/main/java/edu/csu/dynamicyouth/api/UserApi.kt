package edu.csu.dynamicyouth.api

import retrofit2.http.*

interface UserApi {

    /**
     * [loginApi](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/263552714e0)
     */
    @POST("/user/login")
    suspend fun login(
        @Query("code") code: String
    )
}