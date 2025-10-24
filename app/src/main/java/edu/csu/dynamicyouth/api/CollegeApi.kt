package edu.csu.dynamicyouth.api

import edu.csu.dynamicyouth.models.CollegeVO
import retrofit2.http.GET

interface CollegeApi {

    @GET("college")
    suspend fun listAll(): ApiResponse<List<CollegeVO>>
}