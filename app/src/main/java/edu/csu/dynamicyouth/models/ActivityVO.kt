package edu.csu.dynamicyouth.models

import java.time.LocalDateTime


/**
 * 后端返回的活动VO。参考[ActivityVO](https://gitlab.54sher.com/54shenghua/csu-dynamic-youth-backend/-/blob/263b77f399cdd12625d120edc08d58305f9dde3d/src/main/java/com/csu54sher/csudynamicyouth/vo/ActivityVO.java)。
 */
class ActivityVO(
    val id: String? = null,
    val name: String? = null,
    val curNum: Int? = null,
    val limitNum: Int? = null,
    val description: String? = null,
    val heroImg: String? = null,
    val hasApplied: Boolean? = null,
    val hasReviewContent: Boolean? = null,
    val limitTime: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)