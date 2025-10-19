package edu.csu.dynamicyouth.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.api.UserApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    val userApi: UserApi
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


    fun fetchUserInfo() {
        viewModelScope.launch {
            val userInfo = userApi.info()
            _avatarUrl.value = userInfo.data?.avatar ?: DEFAULT_AVATAR
            _username.value = userInfo.data?.nickname
            _college.value = userInfo.data?.college
            _idNumber.value = userInfo.data?.idNumber
        }
    }
}