package edu.csu.dynamicyouth.page

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.BuildConfig
import edu.csu.dynamicyouth.api.RecordApi
import edu.csu.dynamicyouth.api.UserApi
import edu.csu.dynamicyouth.models.RecordVO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    val userApi: UserApi,
    val recordApi: RecordApi
) : ViewModel() {

    private val DEFAULT_AVATAR = "https://54sh.csu.edu.cn/assets/icons/tuanzi_footer.png"

    private val _avatarUrl =
        MutableStateFlow(DEFAULT_AVATAR)
    val avatarUrl: StateFlow<String> = _avatarUrl

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    private val _college = MutableStateFlow<String?>(null)
    val college: StateFlow<String?> = _college

    private val _idNumber = MutableStateFlow<String?>(null)
    val idNumber: StateFlow<String?> = _idNumber

    private val _bestRecord = MutableStateFlow<String?>(null)
    val bestRecord: StateFlow<String?> = _bestRecord

    private val _checkinCount = MutableStateFlow<String?>(null)
    val checkinCount: StateFlow<String?> = _checkinCount


    @SuppressLint("DefaultLocale")
    fun fetchUserInfo() {
        viewModelScope.launch {
            //基本信息
            val userInfo = userApi.info()
            _avatarUrl.value =
                if (userInfo.data?.avatar != null) BuildConfig.BASE_URL + userInfo.data.avatar else DEFAULT_AVATAR
            _username.value = userInfo.data?.nickname
            _college.value = userInfo.data?.college
            _idNumber.value = userInfo.data?.idNumber

            //最佳纪录
            val records = recordApi.listRecord().data
            if (records != null) {
                //找出所用时间最短者
                val shortestRecord = findShortestRecord(records)
                if (shortestRecord != null) {
                    val duration = shortestRecord.endTime!! - shortestRecord.startTime!!
                    //格式化为：mm′ ss″
                    val minutes = String.format("%02d", duration.inWholeMinutes)
                    val seconds = String.format("%02d", duration.inWholeSeconds % 60)
                    _bestRecord.value = "$minutes′ $seconds″"
                }
            }

            //打卡次数
            _checkinCount.value = userInfo.data?.count.toString()
        }

    }

    fun findShortestRecord(records: List<RecordVO>): RecordVO? {
        val validRecords =
            records.filter { it.isValid == true && it.startTime != null && it.endTime != null }
        val shortestRecord = validRecords.minByOrNull { it.endTime!! - it.startTime!! }
        return shortestRecord
    }
}
