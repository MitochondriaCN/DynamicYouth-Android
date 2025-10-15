package edu.csu.dynamicyouth.models

/**
 * UserVO。参考[UserVO](https://gitlab.54sher.com/54shenghua/csu-dynamic-youth-backend/-/blob/263b77f399cdd12625d120edc08d58305f9dde3d/src/main/java/com/csu54sher/csudynamicyouth/vo/UserVO.java)。
 */
data class UserVO (
    /**
     * 用户头像
     */
    val avatar: String? = null,

    /**
     * 学院名称
     */
    val college: String? = null,

    /**
     * 完成次数
     */
    val count: Long? = null,

    /**
     * 创建时间
     */
    val createdAt: String? = null,

    /**
     * 用户 ID，使用雪花算法生成
     */
    val id: String? = null,

    /**
     * 学号 / 工号
     */
    val idNumber: String? = null,

    /**
     * 是否是管理员
     */
    val isAdmin: Boolean? = null,

    /**
     * 是否被封禁
     */
    val isBanned: Boolean? = null,

    /**
     * 是否完善信息
     */
    val isCompleted: Boolean? = null,

    /**
     * 用户昵称
     */
    val nickname: String? = null,

    /**
     * 电话号码
     */
    val phone: String? = null,

    /**
     * 更新时间
     */
    val updatedAt: String? = null
)