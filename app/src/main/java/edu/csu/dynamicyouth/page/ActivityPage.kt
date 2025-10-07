package edu.csu.dynamicyouth.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.EventCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityPage(modifier: Modifier = Modifier) {
    //隐式依赖模式的ViewModel，因为每个页面的ViewModel具有专一性
    val viewModel: ActivityPageViewModel = viewModel()

    val scrollState = rememberScrollState()

    //TODO: 将EventCard的isExpanded提升到ViewModel

    Column(modifier = modifier.verticalScroll(scrollState)) {
        for (a in viewModel.activities) {
            EventCard(
                image = a.heroImg ?: R.drawable.home_promotional,
                title = a.name ?: stringResource(R.string.cannot_fetch_activity),
                description = a.description ?: stringResource(R.string.cannot_fetch_activity)
            ) {
                //TODO: 一个炫酷的放大到详情界面动画
            }

            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}