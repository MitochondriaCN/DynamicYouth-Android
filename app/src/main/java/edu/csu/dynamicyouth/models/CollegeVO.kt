package edu.csu.dynamicyouth.models

import kotlinx.serialization.Serializable

/**
 * [CollegeVO](https://gitlab.54sher.com/54shenghua/csu-dynamic-youth-backend/-/blob/263b77f399cdd12625d120edc08d58305f9dde3d/src/main/java/com/csu54sher/csudynamicyouth/vo/CollegeVO.java)
 */
@Serializable
data class CollegeVO(
    val id: Long? = null,
    val name: String? = null
)
