package edu.csu.dynamicyouth.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class RecordVO(

    val createdAt: String? = null,

    /**
     * 第三个签到点
     */
    val endTime: Instant? = null,

    /**
     * ID **JS 精度问题，前端传递的 ID 会丢失精度，所以使用 String 类型 **
     */
    val id: String? = null,

    /**
     * 是否有效
     */
    val isValid: Boolean? = null,

    /**
     * 第一个签到点
     */
    val startTime: Instant? = null,

    /**
     * 状态
     */
    val status: Status? = null,

    val updatedAt: String? = null
)

/**
 * 状态
 */
@Serializable
enum class Status(val value: String) {
    Cancelled("CANCELLED"),
    Finished("FINISHED"),
    Pending("PENDING");

    companion object {
        public fun fromValue(value: String): Status = when (value) {
            "CANCELLED" -> Cancelled
            "FINISHED" -> Finished
            "PENDING" -> Pending
            else -> throw IllegalArgumentException()
        }
    }
}