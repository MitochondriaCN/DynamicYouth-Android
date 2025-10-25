package edu.csu.dynamicyouth.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.csu.dynamicyouth.BottomNavItem
import edu.csu.dynamicyouth.R
import edu.csu.dynamicyouth.page.ClimbButton
import edu.csu.dynamicyouth.ui.theme.Green
import edu.csu.dynamicyouth.ui.theme.WhiteGrey

@Composable
fun AppFrameworkBottomBar(
    navController: NavHostController,
    navigationItems: List<BottomNavItem>
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        // 避免重复导航到同一目的地
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFrameworkTopBar(title: String = stringResource(id = R.string.app_name)) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE),
            contentColor = Color(0xFF01579B)
        ),
        onClick = {
            isExpanded = !isExpanded
        }) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Campaign, null)

                Spacer(modifier = Modifier.padding(3.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = content)
            }
        }


    }
}

@Composable
fun TextChip(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Green,
    foregroundColor: Color = WhiteGrey
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .padding(
                horizontal = 6.dp,
                vertical = 2.dp
            ),
        text = text,
        color = foregroundColor,
        style = MaterialTheme.typography.labelMedium,
    )
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = 10.dp,
                vertical = 15.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(Modifier.padding(5.dp))
        Text(
            modifier = Modifier.basicMarquee(),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(15.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun Preview() {
    SettingItem(icon = Icons.Default.Campaign, text = "查看所有记录") { }
}