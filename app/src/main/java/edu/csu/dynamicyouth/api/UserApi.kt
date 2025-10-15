package edu.csu.dynamicyouth.api

import edu.csu.dynamicyouth.models.PageResponseListUserAdminVO
import edu.csu.dynamicyouth.models.UserVO
import retrofit2.http.*

interface UserApi {

    /**
     * [loginApi](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/263552714e0)
     */
    @POST("/user/login")
    suspend fun login(
        @Query("code") code: String?
    ): ApiResponse<UserVO>

    //美哉，Kotlin！接口里面能定义类
    data class UpdateDto(
        val nickname: String?,
        val idNumber: String?,
        val college: String?,
        val phone: String?
    )

    /**
     * [updateApi](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/263552715e0)
     */
    @POST("user/update")
    suspend fun update(
        @Body updateDto: UpdateDto
    ): ApiResponse<UserVO>

    /**
     * [listAllUser](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/263552716e0)
     */
    @GET("user/all")
    suspend fun listAll(
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?
    ): ApiResponse<PageResponseListUserAdminVO>

    /**
     * [infoApi](https://s.apifox.cn/79ee79b8-b865-4fc6-907d-08d4a6f999b0/263552717e0)
     */
    @GET("user/info")
    suspend fun info(): ApiResponse<UserVO>
}