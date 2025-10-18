package edu.csu.dynamicyouth.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val code: Int?,
    val msg: String?,
    val data: T?
)