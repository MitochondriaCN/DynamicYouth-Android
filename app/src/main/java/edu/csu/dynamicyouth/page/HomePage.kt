package edu.csu.dynamicyouth.page

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.AnnouncementCard
import edu.csu.dynamicyouth.component.ClimbingDashboard
import edu.csu.dynamicyouth.component.ComposableTencentMap
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Preview(showBackground = true)
@Composable
fun HomePage(modifier: Modifier = Modifier) {

    val viewModel: HomePageViewModel = hiltViewModel()

    val status by viewModel.status.collectAsState()

    val scope = rememberCoroutineScope()
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = true,
            anchors = DraggableAnchors {
                true at 0f
                false at -800f
            })
    }

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
                .verticalScroll(rememberScrollState())
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
                                y = 0
                            )
                        },
                visible = anchoredDraggableState.currentValue,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                AnnouncementCard(
                    modifier.fillMaxWidth(),
                    title = "悦动青春Android APP正在开发中",
                    content = stringResource(R.string.climbing_description)
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            if (status == HomePageStatus.IDLE)
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
            else if (status == HomePageStatus.CLIMBING)
                ClimbingDashboard(
                    elapsedTime = 10.seconds,
                    personalRecord = 24.minutes + 3.seconds,
                    personalAvatar = painterResource(id = R.drawable.xianliticn),
                    personalNickname = "线粒体XianlitiCN",
                    schoolRecord = 3.minutes + 28.seconds,
                    schoolRecordAvatar = painterResource(R.drawable.grtsinry43),
                    schoolRecordNickname = "grtsinry43"
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
                status = status,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {}
            )
        }
    }
}

@Composable
fun ClimbButton(
    modifier: Modifier = Modifier,
    status: HomePageStatus,
    onClick: () -> Unit
) {
    val buttonWidth by animateDpAsState(
        targetValue = if (status == HomePageStatus.CHECKING_IN) 64.dp else 360.dp,
        label = "widthAnimation"
    )
    val buttonHeight by animateDpAsState(
        targetValue = if (status == HomePageStatus.CHECKING_IN) 64.dp else 56.dp,
        label = "heightAnimation"
    )

    Button(
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight),
        contentPadding = PaddingValues(0.dp),
        onClick = {
            onClick()
        },
    ) {
        AnimatedContent(
            targetState = status,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "ButtonContent"
        ) { status ->
            when (status) {
                HomePageStatus.IDLE -> Text(
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 24.sp,
                    text = stringResource(R.string.begin_climb)
                )

                HomePageStatus.CHECKING_IN -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                HomePageStatus.CLIMBING -> Text(
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 24.sp,
                    text = stringResource(R.string.stop_climbing)
                )
            }
        }
    }
}