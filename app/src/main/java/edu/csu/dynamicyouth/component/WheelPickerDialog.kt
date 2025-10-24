package edu.csu.dynamicyouth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.seo4d696b75.compose.material3.picker.Picker
import com.seo4d696b75.compose.material3.picker.PickerDefaults
import com.seo4d696b75.compose.material3.picker.rememberPickerState
import edu.csu.dynamicyouth.R
import kotlinx.collections.immutable.persistentListOf

/**
 * 注意：该组件的列表传入即固定，不能因[values]变化而动态变化。
 */
@Composable
fun WheelPickerDialog(
    modifier: Modifier = Modifier,
    title: String,
    values: List<String>,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {

    val values = remember { persistentListOf(*values.toTypedArray()) }
    val state = rememberPickerState(values)

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        confirmButton = {
            TextButton(
                onClick = { onSubmit(values[state.currentIndex].toString()) }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        text = {
            Picker(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                state = state,
                flingBehavior = PickerDefaults.flingBehavior(
                    state = state,
                    snapDistance = PagerSnapDistance.atMost(1)
                )
            ) { item, enabled ->
                Text(
                    text = item
                )
            }
        }
    )
}