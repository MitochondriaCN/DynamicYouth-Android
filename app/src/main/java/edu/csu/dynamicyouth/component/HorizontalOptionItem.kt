package edu.csu.dynamicyouth.component

import androidx.compose.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HorizontalOptionItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit = {},
    rightAlignedContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))
        rightAlignedContent()
    }
}

@Preview
@Composable
fun HorizontalOptionItemPreview() {
    HorizontalOptionItem(
        title = "查看所有记录",
        onClick = {}
    ) {
        AsyncImage(model = "", contentDescription = null)
    }
}