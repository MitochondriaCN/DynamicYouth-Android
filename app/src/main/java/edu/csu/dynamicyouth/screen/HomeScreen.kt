package edu.csu.dynamicyouth.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.AnnouncementCard

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val showAppDescription = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 3.dp,
                bottom = 3.dp
            )
    ) {
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            AnnouncementCard(
                modifier = Modifier.fillMaxWidth(),
                title = "悦动青春Android APP正在开发",
                content = "浔阳江头夜送客，枫叶荻花秋瑟瑟。主人下马客在船，举酒欲饮无管弦。醉不成欢惨将别，别时茫茫江浸月。"
            )

            Spacer(modifier=Modifier.padding(8.dp))

            AsyncImage(
                model = R.drawable.home_promotional,
                contentDescription = stringResource(R.string.promotion_desc),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomEnd = if (!showAppDescription.value) 10.dp else 0.dp,
                            bottomStart = if (!showAppDescription.value) 10.dp else 0.dp
                        )
                    )
                    .clickable(
                        onClick = { showAppDescription.value = !showAppDescription.value }
                    )
            )
            Spacer(
                modifier.width(8.dp)
            )

            AnimatedVisibility(
                visible = showAppDescription.value,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        text = stringResource(R.string.climbing_description)
                    )
                }
            }

            Text(
                text = "这里是地图",
                Modifier
                    .fillMaxWidth()
            )


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Spacer(
                modifier.width(8.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(R.string.begin_climb))
            }
            Spacer(
                modifier.width(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    HomeScreen()
}