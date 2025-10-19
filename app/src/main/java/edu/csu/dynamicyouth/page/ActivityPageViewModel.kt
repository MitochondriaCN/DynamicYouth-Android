package edu.csu.dynamicyouth.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import edu.csu.dynamicyouth.models.ActivityVO
import kotlinx.datetime.LocalDateTime

/**
 * 活动页面的ViewModel。
 * @author XianlitiCN
 */
@RequiresApi(Build.VERSION_CODES.O)
class ActivityPageViewModel : ViewModel() {

    private val _activities = mutableListOf<ActivityVO>()
    val activities: List<ActivityVO> = _activities

    private val _cardExpandedStatus = mutableStateMapOf<String, Boolean>()
    val cardExpandedStatus: Map<String, Boolean> = _cardExpandedStatus


    init {
        fetchEvents()
    }

    fun handleCardClick(id: String) {
        when (_cardExpandedStatus[id]) {
            null -> _cardExpandedStatus[id] = true
            false -> _cardExpandedStatus[id] = true //断言这个值不可能为false
            true -> {/*TODO: 打开详情页面*/
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchEvents() {
        //TODO: 从后端获取数据

        val dateTime = LocalDateTime(2025, 10, 19, 1, 30, 48)


        //测试数据
        _activities.add(
            ActivityVO(
                id = "1",
                name = "测试活动",
                description = "测试活动描述",
                heroImg = "https://54sh.csu.edu.cn/__local/D/23/05/FD4E90322198F14563B77730979_27F3676B_AFB8F.png",
                limitNum = 10,
                curNum = 5,
                limitTime = dateTime,
                createdAt = dateTime,
                updatedAt = dateTime
            )
        )

        _activities.add(
            ActivityVO(
                id = "2",
                name = "测试活动2",
                description = "测试活动描述2",
                heroImg = "https://54sh.csu.edu.cn/__local/D/23/05/FD4E90322198F14563B77730979_27F3676B_AFB8F.png",
                limitNum = 10,
                curNum = 5,
                limitTime = dateTime,
                createdAt = dateTime,
                updatedAt = dateTime
            )
        )
    }
}