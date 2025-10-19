package edu.csu.dynamicyouth.page

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    val viewModel: ProfilePageViewModel = hiltViewModel()

    val scrollState = rememberScrollState()
    val avatarUrl by viewModel.avatarUrl.collectAsState()
    val username by viewModel.username.collectAsState()
    val college by viewModel.college.collectAsState()
    val idNumber by viewModel.idNumber.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    ProfilePageContent(
        modifier,
        scrollState,
        avatarUrl,
        username,
        college,
        idNumber
    )
}

/**
 * # 为什么要使用[ProfilePageContent]
 *
 * 因为如果直接将[ProfilePageContent]解包放在[ProfilePage]中，由于其中所有数据都直接从
 * [viewModel]中获取，而预览又无法提供[viewModel]，所以不能预览。这时候使用[ProfilePageContent]
 * 包装纯UI内容，就可以在预览中调用之，并传入模拟数据，遂可预览。而从代码架构的角度，[ProfilePage]
 * 只负责数据业务逻辑，而[ProfilePageContent]负责UI逻辑，符合单向数据流，甚为美观。
 */
@Composable
private fun ProfilePageContent(
    modifier: Modifier,
    scrollState: ScrollState,
    avatarUrl: String,
    username: String?,
    college: String?,
    idNumber: String?
) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = college ?: stringResource(R.string.unknown_college)
                    )
                    Icon(
                        modifier = Modifier.padding(start = 15.dp),
                        imageVector = Icons.Outlined.PermIdentity,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = idNumber ?: stringResource(R.string.unknown_id_number)
                    )
                }
            }
        }

        Spacer(Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.WatchLater,
                title = stringResource(R.string.best_record),
                content = "00′ 00″"
            )
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.TaskAlt,
                title = stringResource(R.string.checkin_count),
                content = "0"
            )
        }
    }
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    content: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable(onClick = { onClick() })
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = icon,
                    contentDescription = null,
                )
                Spacer(
                    modifier = Modifier.padding(3.dp)
                )
                Text(
                    modifier = Modifier.basicMarquee(),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
            }

            Spacer(Modifier.padding(10.dp))
            Text(
                text = content,
                fontSize = 40.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ProfilePagePreview() {
    ProfilePageContent(
        modifier = Modifier,
        scrollState = rememberScrollState(),
        avatarUrl = "https://54sh.csu.edu.cn/assets/icons/tuanzi_footer.png",
        username = "XianlitiCN",
        college = "法学院",
        idNumber = null
    )
}