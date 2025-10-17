package edu.csu.dynamicyouth

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import edu.csu.dynamicyouth.ui.theme.DynamicYouthTheme

/**
 * 认证Activity。
 */
class AuthActivity : ComponentActivity() {

    val AUTH_URL = "https://ydqc.csu.edu.cn/api/v1/oauth2/authorization/csu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthPage(AUTH_URL)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPage(url: String, modifier: Modifier = Modifier) {
    DynamicYouthTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.title_activity_auth)) },
                    navigationIcon = {
                        IconButton(onClick = { finishWithoutToken() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            AndroidView(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webViewClient = object : WebViewClient() {

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                Log.d("DEV", "shouldOverrideUrlLoading: ${request?.url}")

                                // 参考URL:
                                // https://ydqc.csu.edu.cn/api/v1/login/oauth2/code/csu?code=OC3778MqnTjk0GZkacjTOtuocIsKviTuUH25&state=JZrQ-DEE528JtQDrBZ0dbXZFYrp0za7t2nCXaB1vg1Y%3D
                                if (request?.url.toString()
                                        .contains("https://ydqc.csu.edu.cn/api/v1/login/oauth2/code/csu?code") == true
                                ) {
                                    val cookies = CookieManager.getInstance()
                                        .getCookie("https://ydqc.csu.edu.cn")
                                    Log.d("DEV", "Cookies: $cookies")
                                    //找到cookies中带有token=...的字符串
                                    val tokenPart = cookies.split(';').find { it.contains("token=") }
                                    //获取等号后面的部分
                                    val token = tokenPart?.split('=')?.get(1)
                                    Log.d("DEV", "Token: $token")
                                }
                                return false
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String?
                            ): Boolean {
                                return false
                            }
                        }
                        loadUrl(url)
                    }
                }
            )
        }
    }
}

fun finishWithoutToken() {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun AuthActivityPreview() {
    //这个预览可能没办法渲染，但是编译出来是好的，所以请酌情使用
    AuthPage("https://ydqc.csu.edu.cn/api/v1/oauth2/authorization/csu")
}