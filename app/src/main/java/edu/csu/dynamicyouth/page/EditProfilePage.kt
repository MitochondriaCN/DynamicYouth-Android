package edu.csu.dynamicyouth.page

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.HorizontalOptionItem
import edu.csu.dynamicyouth.component.InputTextDialog
import edu.csu.dynamicyouth.component.WheelPickerDialog

@Composable
fun EditProfilePage(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: EditProfilePageViewModel = hiltViewModel()

    var showNicknameDialog by remember { mutableStateOf(false) }
    var showCollegeDialog by remember { mutableStateOf(false) }
    var showPhoneNumberDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

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

    val updateResult by viewModel.updateResult.collectAsState()
    LaunchedEffect(updateResult) {
        Log.d("DEV", "updateResult: $updateResult")
        when (updateResult) {
            is UpdateResult.Error -> {
                Toast.makeText(
                    context,
                    (updateResult as UpdateResult.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UpdateResult.Success -> {
                Toast.makeText(
                    context,
                    (updateResult as UpdateResult.Success).message,
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }

            is UpdateResult.Idle -> {}
        }
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
        isSubmitting = viewModel.isSubmitting.collectAsState().value,
        modifiedFields = viewModel.modifiedFields.collectAsState().value,
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
    modifiedFields: EditProfilePageViewModel.ModifiedFields,
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
            title = stringResource(R.string.avatar) + if (modifiedFields.avatar) "*" else "",
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
            title = stringResource(R.string.nickname) + if (modifiedFields.nickname) "*" else "",
            onClick = { onRequestingNicknameChange() }
        ) {
            Text(text = nickname ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.college) + if (modifiedFields.college) "*" else "",
            onClick = { onRequestingCollegeChange() }
        ) {
            Text(text = college ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.phone_number) + if (modifiedFields.phoneNumber) "*" else "",
            onClick = { onRequestingPhoneNumberChange() }
        ) {
            Text(text = phoneNumber ?: stringResource(R.string.unknown))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSubmit() },
            enabled = !isSubmitting && (modifiedFields.avatar || modifiedFields.nickname || modifiedFields.college || modifiedFields.phoneNumber)
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }
}