package edu.csu.dynamicyouth.api

import edu.csu.dynamicyouth.models.RecordVO
import retrofit2.http.GET

interface RecordApi {
    @GET("record")
    suspend fun listRecord(): ApiResponse<List<RecordVO>>

    @GET("record/last")
    suspend fun getLastRecord(): ApiResponse<RecordVO>
}