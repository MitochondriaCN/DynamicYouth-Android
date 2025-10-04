package edu.csu.dynamicyouth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tencent.tencentmap.mapsdk.maps.MapView
import edu.csu.dynamicyouth.component.AnnouncementCard
import edu.csu.dynamicyouth.component.AppFrameworkBottomBar
import edu.csu.dynamicyouth.component.AppFrameworkTopBar
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
            name = stringResource(R.string.my),
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


@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 3.dp,
                bottom = 3.dp
            )
    ) {
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            AnnouncementCard(
                modifier.fillMaxWidth(),
                title = "悦动青春Android APP正在开发中",
                content = "盛世升华，服务中南，不服务中南怎么行呢？按理说你这个级别的同志，还无权服务中南，不知道怎么回事，就把我调到升华工作室来了。很惭愧，就做了一点微小的贡献。"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            AsyncImage(
                model = R.drawable.home_promotional,
                contentDescription = stringResource(R.string.promotion_desc),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = "这里是地图",
                Modifier
                    .fillMaxWidth()
            )

            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    MapView(context)
                })

            Text(text = stringResource(R.string.climbing_description))


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Spacer(
                modifier.width(8.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(R.string.begin_climb))
            }
            Spacer(
                modifier.width(8.dp)
            )
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