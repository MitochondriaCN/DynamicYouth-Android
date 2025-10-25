package edu.csu.dynamicyouth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.ui.theme.DarkGrey
import edu.csu.dynamicyouth.ui.theme.Gold
import edu.csu.dynamicyouth.ui.theme.Green
import edu.csu.dynamicyouth.ui.theme.MidGrey
import edu.csu.dynamicyouth.ui.theme.Red
import edu.csu.dynamicyouth.ui.theme.WhiteGrey
import edu.csu.dynamicyouth.utils.DateTimeUtils.toMmSs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun ClimbingDashboard(
    modifier: Modifier = Modifier,
    elapsedTime: Duration = 0.seconds,
    personalRecord: Duration = 0.seconds,
    personalAvatar: Painter,
    personalNickname: String,
    schoolRecord: Duration = 0.seconds,
    schoolRecordAvatar: Painter,
    schoolRecordNickname: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Green,
            contentColor = WhiteGrey
        )
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 15.dp,
                horizontal = 15.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                RecordBar(
                    record = schoolRecord,
                    avatar = schoolRecordAvatar,
                    nickname = schoolRecordNickname,
                    type = RecordType.SCHOOL
                )
                Spacer(Modifier.padding(5.dp))
                RecordBar(
                    record = personalRecord,
                    avatar = personalAvatar,
                    nickname = personalNickname,
                    type = RecordType.PERSONAL
                )
            }
            Spacer(Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(R.string.climbing),
                    textAlign = TextAlign.Right,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    //转换为mm′ ss″格式
                    text = elapsedTime.toMmSs(),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}


@Composable
fun RecordBar(
    modifier: Modifier = Modifier,
    record: Duration = 0.seconds,
    avatar: Painter,
    nickname: String,
    type: RecordType = RecordType.PERSONAL
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape),
            painter = avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(5.dp))
        Column {
            Text(
                text = nickname,
                fontSize = 13.sp,
                maxLines = 1,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.toMmSs(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                Spacer(Modifier.width(5.dp))
                RecordTypeIndicator(
                    type = type
                )
            }
        }
    }
}

@Composable
fun RecordTypeIndicator(
    modifier: Modifier = Modifier,
    type: RecordType = RecordType.PERSONAL
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(if (type == RecordType.PERSONAL) MidGrey else Gold)
            .padding(horizontal = 2.dp, vertical = 1.dp),
        text = if (type == RecordType.PERSONAL) stringResource(R.string.personal_record)
        else stringResource(R.string.school_record),
        color = WhiteGrey,
        fontWeight = FontWeight.Bold,
        fontSize = 9.sp,
        style = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
    )
}

enum class RecordType {
    PERSONAL,
    SCHOOL
}

@Preview
@Composable
fun ClimbingDashboardPreview() {
    ClimbingDashboard(
        elapsedTime = 10.seconds,
        personalRecord = 5.minutes + 3.seconds,
        personalAvatar = painterResource(id = R.drawable.xianliticn),
        personalNickname = "线粒体XianlitiCN",
        schoolRecord = 3.minutes + 28.seconds,
        schoolRecordAvatar = painterResource(R.drawable.xianliticn),
        schoolRecordNickname = "grtsinry43",
    )
}