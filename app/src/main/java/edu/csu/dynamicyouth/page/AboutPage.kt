package edu.csu.dynamicyouth.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.csu.dynamicyouth.R

@Composable
fun AboutPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.padding(30.dp))
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.padding(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.weight(1f))
            Spacer(Modifier.padding(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White),
                    painter = painterResource(R.drawable.sher_logo),
                    contentDescription = null
                )
                Spacer(Modifier.padding(5.dp))
                Text(
                    text = stringResource(R.string.sher),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(Modifier.padding(10.dp))
            HorizontalDivider(Modifier.weight(1f))
        }

        Spacer(Modifier.padding(15.dp))

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                DeveloperInfo(
                    avatar = painterResource(R.drawable.grtsinry43),
                    name = "grtsinry43",
                    career = stringResource(R.string.full_stack_dveloper)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.mufenqwq),
                    name = "mufenqwq",
                    career = stringResource(R.string.full_stack_dveloper)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.unioreox),
                    name = "UniOreoX",
                    career = stringResource(R.string.android_developer)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.dislinksforza),
                    name = "DislinkSforza",
                    career = stringResource(R.string.tester)
                )
            }
            Spacer(Modifier.padding(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                DeveloperInfo(
                    avatar = painterResource(R.drawable.steamfinder),
                    name = "SteamFinder",
                    career = stringResource(R.string.full_stack_dveloper)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.kyliancc),
                    name = "kyliancc",
                    career = stringResource(R.string.full_stack_dveloper)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.yangpixi),
                    name = "yangpixi",
                    career = stringResource(R.string.backend_developer)
                )
                DeveloperInfo(
                    avatar = painterResource(R.drawable.xianliticn),
                    name = "XianlitiCN",
                    career = stringResource(R.string.android_developer)
                )
            }
        }

        Spacer(Modifier.padding(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Copyright © 2001-2025 升华工作室",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.padding(10.dp))
    }
}

@Composable
fun DeveloperInfo(
    modifier: Modifier = Modifier,
    avatar: Painter,
    name: String,
    career: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = avatar,
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.padding(3.dp))
        Column {
            Text(
                modifier = Modifier.basicMarquee(),
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.basicMarquee(),
                text = career,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun PreviewAboutPage() {
    AboutPage()
}