package edu.csu.dynamicyouth.page

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.TextChip

@Composable
fun AboutPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = stringResource(R.string.about))
    }
}

@Preview
@Composable
fun PreviewAboutPage() {
    AboutPage()
}