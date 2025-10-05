package edu.csu.dynamicyouth.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.AnnouncementCard
import edu.csu.dynamicyouth.component.ComposableTencentMap

@Preview(showBackground = true)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    var hasAnnouncement by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 3.dp,
                bottom = 3.dp
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 6.dp)
        ) {
            AnimatedVisibility(
                visible = hasAnnouncement,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally () + fadeOut()
            ) {
                AnnouncementCard(
                    modifier.fillMaxWidth(),
                    title = "悦动青春Android APP正在开发中",
                    content = stringResource(R.string.climbing_description)
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            AsyncImage(
                model = R.drawable.home_promotional,
                contentDescription = stringResource(R.string.promotion_desc),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(true, onClick = {
                        // 暂时使用图片点击来展示公告进入和退出动画
                        hasAnnouncement = !hasAnnouncement
                    })
            )

            Spacer(modifier = Modifier.padding(6.dp))

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                ComposableTencentMap(
                    modifier = Modifier.fillMaxSize()
                )
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(
                    start = 8.dp,
                    end = 8.dp),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(R.string.begin_climb))
            }
        }
    }
}