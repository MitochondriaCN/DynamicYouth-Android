package edu.csu.dynamicyouth.api

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)