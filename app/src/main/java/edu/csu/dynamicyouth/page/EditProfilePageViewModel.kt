package edu.csu.dynamicyouth.page

import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.BuildConfig
import edu.csu.dynamicyouth.api.UploadFileApi
import edu.csu.dynamicyouth.api.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.csu.dynamicyouth.api.CollegeApi

@HiltViewModel
class EditProfilePageViewModel @Inject constructor(
    private val userApi: UserApi,
    private val uploadFileApi: UploadFileApi,
    private val collegeApi: CollegeApi,
    @ApplicationContext private val context: Context
) : ViewModel() {

    //唉唉，样板代码
    //其实可以更abstract的，都怪我，弄得到处都是
    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl

    private val _nickname = MutableStateFlow<String?>(null)
    val nickname: StateFlow<String?> = _nickname

    private val _college = MutableStateFlow<String?>(null)
    val college: StateFlow<String?> = _college

    private val _csuColleges = MutableStateFlow<List<String>?>(null)
    val csuColleges: StateFlow<List<String>?> = _csuColleges

    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber: StateFlow<String?> = _phoneNumber

    private val _modifiedFields = MutableStateFlow(ModifiedFields())
    val modifiedFields: StateFlow<ModifiedFields> = _modifiedFields


    fun updateAvatar(localUri: Uri) {
        _avatarUrl.value = localUri.toString()
        _modifiedFields.value = _modifiedFields.value.copy(avatar = true)
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
        _modifiedFields.value = _modifiedFields.value.copy(nickname = true)
    }


    fun updateCollege(college: String) {
        _college.value = college
        _modifiedFields.value = _modifiedFields.value.copy(college = true)
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        _modifiedFields.value = _modifiedFields.value.copy(phoneNumber = true)
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            val userInfo = userApi.info().data
            if (userInfo != null) {
                _avatarUrl.value = BuildConfig.BASE_URL + userInfo.avatar
                _nickname.value = userInfo.nickname
                _college.value = userInfo.college
                _phoneNumber.value = userInfo.phone
            }

            //获取学院列表
            val collegeList = collegeApi.listAll().data
            if (collegeList != null) {
                //去null
                _csuColleges.value = collegeList.filter { it.name != null }.map { it.name!! }
            }
        }
    }

    private fun uploadNewAvatar(localUri: Uri) {
        viewModelScope.launch {
            try {
                context.contentResolver.openInputStream(localUri)?.use { inputStream ->
                    val fileBytes = inputStream.readBytes()
                    val requestFile = fileBytes.toRequestBody(
                        context.contentResolver.getType(localUri)?.toMediaTypeOrNull()
                    )

                    //从URI获取文件名
                    var fileName = "unknown"
                    context.contentResolver.query(localUri, null, null, null, null)?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            fileName =
                                cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                        }
                    }
                    val body = MultipartBody.Part.createFormData("file", fileName, requestFile)
                    val response = uploadFileApi.uploadAvatar(body)
                    _avatarUrl.value = BuildConfig.BASE_URL + response.data
                }
            } catch (e: Exception) {
                Log.e("EditProfilePageViewModel", "Avatar upload failed", e)
            }
        }
    }

    fun checkAndSubmit() {
        //TODO
    }

    /**
     * 修改过的字段的情况。
     */
    data class ModifiedFields(
        var avatar: Boolean = false,
        var nickname: Boolean = false,
        var college: Boolean = false,
        var phoneNumber: Boolean = false
    )
}