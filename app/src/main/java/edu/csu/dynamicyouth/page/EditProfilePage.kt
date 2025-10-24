package edu.csu.dynamicyouth.page

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.HorizontalOptionItem
import edu.csu.dynamicyouth.component.InputTextDialog
import edu.csu.dynamicyouth.component.WheelPickerDialog

@Composable
fun EditProfilePage(
    modifier: Modifier = Modifier
) {

    val viewModel: EditProfilePageViewModel = hiltViewModel()

    var showNicknameDialog by remember { mutableStateOf(false) }
    var showCollegeDialog by remember { mutableStateOf(false) }
    var showPhoneNumberDialog by remember { mutableStateOf(false) }


    val avatarPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.updateAvatar(uri)
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    EditProfilePageContent(
        modifier = modifier,
        avatar = viewModel.avatarUrl.collectAsState().value,
        nickname = viewModel.nickname.collectAsState().value,
        college = viewModel.college.collectAsState().value,
        phoneNumber = viewModel.phoneNumber.collectAsState().value,
        onRequestingAvatarChange = {
            avatarPicker.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
            )
        },
        onRequestingNicknameChange = {
            showNicknameDialog = true
        },
        onRequestingCollegeChange = {
            showCollegeDialog = true
        },
        onRequestingPhoneNumberChange = {
            showPhoneNumberDialog = true
        },
        onSubmit = { viewModel.checkAndSubmit() }
    )

    if (showNicknameDialog) {
        InputTextDialog(
            title = stringResource(R.string.nickname),
            initialValue = viewModel.nickname.collectAsState().value ?: "",
            onDismiss = { showNicknameDialog = false },
            onSubmit = {
                showNicknameDialog = false
                viewModel.updateNickname(it)
            }
        )
    }

    val csuColleges = viewModel.csuColleges.collectAsState().value
    if (showCollegeDialog && csuColleges != null) {
        WheelPickerDialog(
            title = stringResource(R.string.college),
            values = csuColleges,
            onDismiss = { showCollegeDialog = false },
            onSubmit = {
                showCollegeDialog = false
                viewModel.updateCollege(it)
            }
        )
    }

    if (showPhoneNumberDialog) {
        InputTextDialog(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            title = stringResource(R.string.phone_number),
            initialValue = viewModel.phoneNumber.collectAsState().value ?: "",
            onDismiss = { showPhoneNumberDialog = false },
            onSubmit = {
                showPhoneNumberDialog = false
                viewModel.updatePhoneNumber(it)
            }
        )
    }
}

@Composable
private fun EditProfilePageContent(
    modifier: Modifier = Modifier,
    avatar: Any?,
    nickname: String?,
    college: String?,
    phoneNumber: String?,
    isSubmitting: Boolean = false,
    onRequestingAvatarChange: () -> Unit = {},
    onRequestingNicknameChange: () -> Unit = {},
    onRequestingCollegeChange: () -> Unit = {},
    onRequestingPhoneNumberChange: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        HorizontalOptionItem(
            title = stringResource(R.string.avatar),
            onClick = { onRequestingAvatarChange() }
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp),
                model = avatar,
                contentDescription = null
            )
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.nickname),
            onClick = { onRequestingNicknameChange() }
        ) {
            Text(text = nickname ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.college),
            onClick = { onRequestingCollegeChange() }
        ) {
            Text(text = college ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.phone_number),
            onClick = { onRequestingPhoneNumberChange() }
        ) {
            Text(text = phoneNumber ?: stringResource(R.string.unknown))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSubmit() },
            enabled = !isSubmitting
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }
}