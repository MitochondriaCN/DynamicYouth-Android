package edu.csu.dynamicyouth.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import edu.csu.dynamicyouth.models.ActivityVO
import java.time.LocalDateTime

/**
 * 活动页面的ViewModel。
 * @author XianlitiCN
 */
@RequiresApi(Build.VERSION_CODES.O)
class ActivityPageViewModel : ViewModel() {

    private val _activities = mutableListOf<ActivityVO>()
    val activities: List<ActivityVO> = _activities

    init {
        fetchEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchEvents() {
        //TODO: 从后端获取数据

        //测试数据
        _activities.add(
            ActivityVO(
                name = "测试活动",
                description = "测试活动描述",
                heroImg = "https://54sh.csu.edu.cn/__local/D/23/05/FD4E90322198F14563B77730979_27F3676B_AFB8F.png",
                limitNum = 10,
                curNum = 5,
                limitTime = LocalDateTime.now().plusDays(1),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )

        _activities.add(
            ActivityVO(
                name = "测试活动2",
                description = "测试活动描述2",
                heroImg = "https://54sh.csu.edu.cn/__local/D/23/05/FD4E90322198F14563B77730979_27F3676B_AFB8F.png",
                limitNum = 10,
                curNum = 5,
                limitTime = LocalDateTime.now().plusDays(1),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }
}