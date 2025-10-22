package edu.csu.dynamicyouth.page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.BuildConfig
import edu.csu.dynamicyouth.api.UserApi
import edu.csu.dynamicyouth.network.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfilePageViewModel @Inject constructor(
    private val userApi: UserApi,
) : ViewModel() {

    //唉唉，样板代码
    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl

    private val _nickname = MutableStateFlow<String?>(null)
    val nickname: StateFlow<String?> = _nickname

    private val _college = MutableStateFlow<String?>(null)
    val college: StateFlow<String?> = _college

    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber: StateFlow<String?> = _phoneNumber

    fun fetchUserInfo() {
        viewModelScope.launch {
            val userInfo = userApi.info().data
            if (userInfo != null) {
                _avatarUrl.value = BuildConfig.BASE_URL + userInfo.avatar
                _nickname.value = userInfo.nickname
                _college.value = userInfo.college
                _phoneNumber.value = userInfo.phone
            }
        }
    }

    fun checkAndSubmit() {
        //TODO
    }
}