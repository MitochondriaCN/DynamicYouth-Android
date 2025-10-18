package edu.csu.dynamicyouth.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val viewModel: ProfilePageViewModel = hiltViewModel()

    val avatarUrl by viewModel.avatarUrl.collectAsState()
    val username by viewModel.username.collectAsState()
    val college by viewModel.college.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            ) {
                Text(
                    text = username ?: stringResource(R.string.not_logged_in),
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = college ?: stringResource(R.string.unknown_college)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePagePreview() {
    ProfilePage()
}