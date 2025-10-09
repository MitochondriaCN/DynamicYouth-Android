package edu.csu.dynamicyouth.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * 活动卡片。
 * @param image 活动图片。
 * @param title 活动标题，最多显示2行。
 * @param description 活动描述，`isExpanded`为true时才显示，最多显示3行。
 * @param isExpanded 是否展开。
 */
@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    image: Any,
    title: String,
    description: String,
    isExpanded: Boolean = false,
    onClick: () -> Unit = {},
) {
    val animationDurationMillis = 300
    val animatedRatio by animateFloatAsState(
        targetValue = if (isExpanded) 16f / 9f else 8f / 3f,
        animationSpec = tween(animationDurationMillis)
    )

    Card(
        modifier = modifier
            .animateContentSize(tween(animationDurationMillis))
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = animatedRatio),
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                )

                if (isExpanded) {
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}