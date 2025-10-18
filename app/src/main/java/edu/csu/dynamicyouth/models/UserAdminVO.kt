package edu.csu.dynamicyouth.models

data class UserAdminVO (
    /**
     * 用户头像
     */
    val avatar: String? = null,

    /**
     * 最佳记录
     */
    val bestRecord: Long? = null,

    /**
     * 学院 ID
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
     * 第三方登录 ID
     */
    val oauthID: String? = null,

    /**
     * 第三方登录提供者
     */
    val oauthProvider: String? = null,

    /**
     * 电话号码
     */
    val phone: String? = null,

    /**
     * 真实姓名
     */
    val realName: String? = null,

    val roles: List<String>? = null,

    /**
     * 更新时间
     */
    val updatedAt: String? = null,

    /**
     * 管理用户唯一标识，可以为空
     */
    val username: String? = null
)