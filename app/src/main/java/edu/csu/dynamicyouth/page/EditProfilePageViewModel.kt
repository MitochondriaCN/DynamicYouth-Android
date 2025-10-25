package edu.csu.dynamicyouth.page

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.csu.dynamicyouth.BuildConfig
import edu.csu.dynamicyouth.api.CollegeApi
import edu.csu.dynamicyouth.api.UploadFileApi
import edu.csu.dynamicyouth.api.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

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

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _updateResult = MutableStateFlow<UpdateResult>(UpdateResult.Idle)
    val updateResult: StateFlow<UpdateResult> = _updateResult

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

    private suspend fun uploadNewAvatar(localUri: Uri): String {
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
                return response.data ?: throw Exception("上传头像失败")
            }
        } catch (e: Exception) {
            throw Exception("上传头像失败", e)
        }
        throw Exception("上传头像失败")
    }

    /**
     * 检查数据合法性并上传。使用[updateResult]返回结果。
     */
    fun checkAndSubmit() {
        _isSubmitting.value = true

        var avatar = _avatarUrl.value
        val nickname = _nickname.value
        val college = _college.value
        val phoneNumber = _phoneNumber.value

        viewModelScope.launch {
            //必须对所有数据检查合法性，因为所有数据都要填入request
            //即便这项数据没有被修改，也必须检查

            //nickname
            if (nickname?.isEmpty() ?: true) {
                _isSubmitting.value = false
                _updateResult.value = UpdateResult.Error("昵称不能为空")
                return@launch
            }

            //college
            if (college?.isEmpty() ?: true) {
                _isSubmitting.value = false
                _updateResult.value = UpdateResult.Error("学院不能为空")
                return@launch
            }

            //phoneNumber
            //使用正则表达式检查手机号
            if (phoneNumber?.isEmpty() ?: true || !phoneNumber.matches(Regex("^1[3-9]\\d{9}$"))) {
                _isSubmitting.value = false
                _updateResult.value = UpdateResult.Error("手机号格式不正确")
                return@launch
            }

            //avatar，若已修改，先上传再检查URL合法性，若未修改，直接检查URL合法性
            if (_modifiedFields.value.avatar) {
                try {
                    avatar = uploadNewAvatar(avatar!!.toUri())
                } catch (e: Exception) {
                    _isSubmitting.value = false
                    _updateResult.value = UpdateResult.Error("上传头像失败：${e.message}")
                    return@launch
                }
            }
            if (avatar?.isEmpty() ?: true) {
                _isSubmitting.value = false
                _updateResult.value = UpdateResult.Error("头像不能为空")
                return@launch
            }

            try {
                val response = userApi.update(
                    UserApi.UpdateDto(
                        nickname = nickname,
                        avatar = if (_modifiedFields.value.avatar) avatar else null,
                        college = college,
                        phone = phoneNumber
                    )
                )
                if (response.code != 0) {
                    _isSubmitting.value = false
                    _updateResult.value = UpdateResult.Error("更新用户信息失败")
                    return@launch
                }
            } catch (e: Exception) {
                _isSubmitting.value = false
                _updateResult.value = UpdateResult.Error("更新用户信息失败：${e.message}")
                return@launch
            }
            _isSubmitting.value = false
            _updateResult.value = UpdateResult.Success("更新用户信息成功")
        }
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

sealed class UpdateResult {
    data class Success(val message: String) : UpdateResult()
    data class Error(val message: String) : UpdateResult()
    object Idle : UpdateResult()
}
