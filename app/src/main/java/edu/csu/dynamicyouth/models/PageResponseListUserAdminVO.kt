package edu.csu.dynamicyouth.models

import kotlinx.serialization.Serializable

@Serializable
data class PageResponseListUserAdminVO(
    val current: Long?,
    val data: List<UserAdminVO>?,
    val pageSize: Long?,
    val total: Long?
)