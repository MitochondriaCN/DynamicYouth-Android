package edu.csu.dynamicyouth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistantPhoto
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tencent.tencentmap.mapsdk.maps.TencentMapInitializer
import edu.csu.dynamicyouth.component.AppFrameworkBottomBar
import edu.csu.dynamicyouth.component.AppFrameworkTopBar
import edu.csu.dynamicyouth.page.EventPage
import edu.csu.dynamicyouth.page.HomePage
import edu.csu.dynamicyouth.page.ProfilePage
import edu.csu.dynamicyouth.page.RankingPage
import edu.csu.dynamicyouth.ui.theme.DynamicYouthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 同意腾讯地图隐私政策
        TencentMapInitializer.setAgreePrivacy(this, true)
        TencentMapInitializer.start(this)
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
            icon = Icons.Filled.Home,
            name = stringResource(R.string.homepage),
            route = "homepage",
        ),
        BottomNavItem(
            icon = Icons.Filled.AssistantPhoto,
            name = stringResource(R.string.event),
            route = "event",
        ),
        BottomNavItem(
            icon = Icons.Filled.BarChart,
            name = stringResource(R.string.ranking),
            route = "ranking",
        ),
        BottomNavItem(
            icon = Icons.Filled.Contacts,
            name = stringResource(R.string.profile),
            route = "profile"
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFramework()
}