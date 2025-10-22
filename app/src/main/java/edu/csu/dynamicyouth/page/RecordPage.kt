package edu.csu.dynamicyouth.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.component.TextChip
import edu.csu.dynamicyouth.models.RecordStatus
import edu.csu.dynamicyouth.utils.DateTimeUtils
import kotlinx.datetime.Instant
import edu.csu.dynamicyouth.models.*
import edu.csu.dynamicyouth.ui.theme.Green
import edu.csu.dynamicyouth.ui.theme.Red
import edu.csu.dynamicyouth.ui.theme.Yellow
import kotlinx.datetime.Clock


@Composable
fun RecordPage(modifier: Modifier = Modifier) {

    val viewModel: RecordPageViewModel = hiltViewModel()

    val records by viewModel.records.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecords()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = records,
            key = { record: RecordVO -> record.id!! }
        ) {
            RecordItem(
                modifier = Modifier.fillMaxWidth(),
                id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                recordStatus = it.status
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
fun RecordItem(
    modifier: Modifier = Modifier,
    id: String?,
    startTime: Instant?,
    endTime: Instant?,
    recordStatus: RecordStatus?
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (endTime != null && startTime != null) (endTime - startTime)
                    .toComponents { minutes, seconds, _ ->
                        String.format("%02d′ %02d″", minutes, seconds)
                    } else "00′ 00″",
                fontSize = 25.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.padding(5.dp))
            TextChip(
                text = when (recordStatus) {
                    RecordStatus.Finished -> stringResource(R.string.finished)
                    RecordStatus.Cancelled -> stringResource(R.string.cancelled)
                    RecordStatus.Pending -> stringResource(R.string.pending)
                    null -> stringResource(R.string.unknown)
                },
                backgroundColor = when (recordStatus) {
                    RecordStatus.Finished -> Green
                    RecordStatus.Cancelled, null -> Red
                    RecordStatus.Pending -> Yellow
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Flag,
                contentDescription = null
            )
            Spacer(
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = stringResource(R.string.started_at)
                        + if (startTime != null)
                    DateTimeUtils.convertInstantToLocalDateTimeFormat(startTime)
                else stringResource(R.string.unknown),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.SportsScore,
                contentDescription = null
            )
            Spacer(
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = stringResource(R.string.ended_at)
                        + if (endTime != null)
                    DateTimeUtils.convertInstantToLocalDateTimeFormat(endTime)
                else stringResource(R.string.unknown),
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecordPage() {
    RecordItem(
        modifier = Modifier.fillMaxWidth(),
        id = "1",
        startTime = Clock.System.now(),
        endTime = Clock.System.now(),
        recordStatus = RecordStatus.Finished
    )
}