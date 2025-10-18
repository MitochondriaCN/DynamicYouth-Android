package edu.csu.dynamicyouth.models

data class PageResponseListUserAdminVO(
    val current: Long?,
    val data: List<UserAdminVO>?,
    val pageSize: Long?,
    val total: Long?
)