package edu.csu.dynamicyouth.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import edu.csu.dynamicyouth.R

@Composable
fun InputTextDialog(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {

    //Dialog这种东西，血液里流淌着时间切片的基因，所以不需要提升状态，自己维护text即可
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        confirmButton = {
            TextButton(
                onClick = { onSubmit(text) }
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
            TextField(
                value = text,
                onValueChange = { text = it },
                keyboardOptions = keyboardOptions,
                singleLine = true
            )
        }
    )
}
