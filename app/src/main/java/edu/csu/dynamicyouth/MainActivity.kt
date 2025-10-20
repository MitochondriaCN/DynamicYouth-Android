package edu.csu.dynamicyouth

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssistantPhoto
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.tencentmap.mapsdk.maps.TencentMapInitializer
import dagger.hilt.android.AndroidEntryPoint
import edu.csu.dynamicyouth.component.AppFrameworkBottomBar
import edu.csu.dynamicyouth.component.AppFrameworkTopBar
import edu.csu.dynamicyouth.component.PermissionDialog
import edu.csu.dynamicyouth.page.ActivityPage
import edu.csu.dynamicyouth.page.HomePage
import edu.csu.dynamicyouth.page.ProfilePage
import edu.csu.dynamicyouth.page.RankingPage
import edu.csu.dynamicyouth.ui.theme.DynamicYouthTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 同意腾讯地图隐私政策
        TencentMapInitializer.setAgreePrivacy(this, true)
        TencentMapInitializer.start(this)
        // 同意腾讯位置服务隐私政策
        TencentLocationManager.setUserAgreePrivacy(true)
        setContent {
            AppFramework()
        }
    }
}

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)

/**
 * 主程序框架
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppFramework(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val isDialogShown = remember { mutableStateOf(false) }

    val deniedMessage = stringResource(R.string.permission_denied)

    PermissionDialog(
        permissions =
            mapOf(
                Manifest.permission.ACCESS_FINE_LOCATION to stringResource(R.string.ACCESS_FINE_LOCATION),
                Manifest.permission.ACCESS_COARSE_LOCATION to stringResource(R.string.ACCESS_COARSE_LOCATION),
            ),
        policies =
            listOf(
                stringResource(R.string.TencentMapSDK_Permission),
                stringResource(R.string.TencentLocationSDK_Permission),
            ),
        isShown = isDialogShown.value,
        onDismissRequest = {
            isDialogShown.value = true
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = deniedMessage,
                    duration = SnackbarDuration.Long,
                    withDismissAction = true
                )
            }
        }
    )

    val navigationItems = listOf(
        BottomNavItem(
            icon = Icons.Filled.Home,
            name = stringResource(R.string.homepage),
            route = "homepage",
        ),
        BottomNavItem(
            icon = Icons.Filled.AssistantPhoto,
            name = stringResource(R.string.event),
            route = "activity",
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
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = { AppFrameworkTopBar() },
            bottomBar = { AppFrameworkBottomBar(navController, navigationItems) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "homepage",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ) {
                composable(route = "homepage") { HomePage() }
                composable("ranking") { RankingPage() }
                composable("activity") { ActivityPage() }
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