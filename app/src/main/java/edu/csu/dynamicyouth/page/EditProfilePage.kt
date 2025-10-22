package edu.csu.dynamicyouth.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.HorizontalOptionItem

@Composable
fun EditProfilePage(
    modifier: Modifier = Modifier
) {

    val viewModel: EditProfilePageViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    EditProfilePageContent(
        avatar = viewModel.avatarUrl.collectAsState().value,
        nickname = viewModel.nickname.collectAsState().value,
        college = viewModel.college.collectAsState().value,
        phoneNumber = viewModel.phoneNumber.collectAsState().value,
        onSubmit = { viewModel.checkAndSubmit() }
    )
}

@Composable
private fun EditProfilePageContent(
    modifier: Modifier = Modifier,
    avatar: Any?,
    nickname: String?,
    college: String?,
    phoneNumber: String?,
    isSubmitting: Boolean = false,
    onSubmit: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        HorizontalOptionItem(
            title = stringResource(R.string.avatar),
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
            title = stringResource(R.string.nickname)
        ) {
            Text(text = nickname ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.college)
        ) {
            Text(text = college ?: stringResource(R.string.unknown))
        }

        HorizontalDivider()

        HorizontalOptionItem(
            title = stringResource(R.string.phone_number)
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