package edu.csu.dynamicyouth.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
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


@Preview
@Composable
fun Preview() {
    AnnouncementCard(
        title = "中南小团子2025年登山活动开始",
        content = "春山如笑，青春激扬。3月25日上午9时，由校团委牵头、校工会、体育教研部、教师工作部、本科生院、研究生院等单位协同主办的中南大学2025年春季登山节启动仪式在岳麓山校区举行，各学院登山代表350余人齐聚一堂，共同见证这场以“趁青春 去登山”为主题的校园盛事。校党委副书记李景升、各有关职能部门、学院主要负责人出席开幕仪式，滴滴出行、中国联通等赞助单位代表共同参与开幕式活动。",
    )
}