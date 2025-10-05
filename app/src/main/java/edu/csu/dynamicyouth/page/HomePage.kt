package edu.csu.dynamicyouth.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tencent.mapsdk.internal.ab
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.AnnouncementCard
import edu.csu.dynamicyouth.component.ClimbButton
import edu.csu.dynamicyouth.component.ComposableTencentMap
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    var climbStatus by remember { mutableStateOf("normal") }
    val scope = rememberCoroutineScope()
    val anchoredDraggableState = remember { AnchoredDraggableState(
        initialValue = true,
        anchors = DraggableAnchors {
            true at 0f
            false at -800f
        }) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 3.dp,
                bottom = 6.dp
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 6.dp)
        ) {
            AnimatedVisibility(
                modifier =
                    Modifier
                        .anchoredDraggable(
                            anchoredDraggableState,
                            orientation = Orientation.Horizontal
                        )
                        .offset {
                            IntOffset(
                                x = anchoredDraggableState
                                    .requireOffset()
                                    .roundToInt(),
                                y = 0)
                        }
                ,
                visible = anchoredDraggableState.currentValue,
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
                        scope.launch {
                            anchoredDraggableState.snapTo(targetValue = !anchoredDraggableState.currentValue)
                        }
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
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
        ) {
            ClimbButton(
                status = climbStatus,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    climbStatus = if (climbStatus == "loading") {
                        "normal"
                    } else {
                        "loading"
                    }
                }
            )
        }
    }
}