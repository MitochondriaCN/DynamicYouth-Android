package edu.csu.dynamicyouth.models

import kotlinx.serialization.Serializable

/**
 * UserVO。参考[UserVO](https://gitlab.54sher.com/54shenghua/csu-dynamic-youth-backend/-/blob/263b77f399cdd12625d120edc08d58305f9dde3d/src/main/java/com/csu54sher/csudynamicyouth/vo/UserVO.java)。
 */
@Serializable
data class UserVO(
    /**
     * 用户头像
     */
    val avatar: String?,
    /**
     * 学院名称
     */
    val college: String?,

    /**
     * 完成次数
     */
    val count: Float?,

    /**
     * 创建时间
     */
    val createdAt: String?,

    /**
     * 用户 ID，使用雪花算法生成
     */
    val id: String?,

    /**
     * 学号 / 工号
     */
    val idNumber: String?,

    /**
     * 是否是管理员
     */
    val isAdmin: Boolean?,

    /**
     * 是否被封禁
     */
    val isBanned: Boolean?,

    /**
     * 是否完善信息
     */
    val isCompleted: Boolean?,

    /**
     * 用户昵称
     */
    val nickname: String?,

    /**
     * 电话号码
     */
    val phone: String?,

    /**
     * 更新时间
     */
    val updatedAt: String?
)