package edu.csu.dynamicyouth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistantPhoto
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.csu.dynamicyouth.ui.theme.DynamicYouthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFramework()
        }
    }
}

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)

/**
 * 主程序框架
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFramework(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navigationItems = listOf(
        BottomNavItem(
            stringResource(R.string.homepage),
            "homepage",
            Icons.Filled.Home
        ),
        BottomNavItem(
            stringResource(R.string.event),
            "event", //这里采取event作为“活动”的翻译，与现有前后端项目约定保持一致
            Icons.Filled.AssistantPhoto
        ),
        BottomNavItem(
            stringResource(R.string.ranking),
            "ranking",
            Icons.Filled.BarChart
        ),
        BottomNavItem(
            stringResource(R.string.my),
            "profile",
            Icons.Filled.Contacts
        )
    )

    DynamicYouthTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { AppFrameworkTopBar() },
            bottomBar = { AppFrameworkBottomBar(navController, navigationItems) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "homepage",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                composable("homepage") { HomePage() }
                composable("ranking") { RankingPage() }
                composable("event") { EventPage() }
                composable("profile") { ProfilePage() }
            }
        }
    }
}


@Composable
private fun AppFrameworkBottomBar(
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
private fun AppFrameworkTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "这里是地图",
            Modifier
                .fillMaxWidth()
        )

        Text(text = stringResource(R.string.climbing_description))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
        ) {
            Text(text = stringResource(R.string.begin_climb))
        }
    }
}

@Composable
fun EventPage() {
    Text(stringResource(R.string.event))
}

@Composable
fun RankingPage() {
    Text(stringResource(R.string.ranking))
}

@Composable
fun ProfilePage() {
    Text(stringResource(R.string.my))
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFramework()
}