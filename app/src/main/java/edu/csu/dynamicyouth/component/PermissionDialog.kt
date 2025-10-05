package edu.csu.dynamicyouth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import edu.csu.dynamicyouth.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDialog(permissions: Map<String, String>, policies: List<String>, isShown: Boolean, onDismissRequest: () -> Unit) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions.keys.toList())

    if (!multiplePermissionsState.allPermissionsGranted && !isShown) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.Info, contentDescription = "Alert")
            },
            title = {
                Text(
                    text = stringResource(R.string.permission_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )
            },
            text = {
                Column {
                    permissions.values.forEach {
                        Spacer(modifier = Modifier.fillMaxWidth()
                            .height(6.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            )
                    }
                    policies.forEach {
                        Spacer(modifier = Modifier.fillMaxWidth()
                            .height(6.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                Button(
                    onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }
                ) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
