package edu.csu.dynamicyouth

import android.content.Intent
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
import dagger.hilt.android.AndroidEntryPoint
import edu.csu.dynamicyouth.network.TokenManager
import edu.csu.dynamicyouth.ui.theme.DynamicYouthTheme
import javax.inject.Inject

/**
 * 认证Activity。
 */
@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    val AUTH_URL = "https://ydqc.csu.edu.cn/api/v1/oauth2/authorization/csu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthPage(AUTH_URL)
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
                            IconButton(onClick = { finish() }) {
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
                                            .contains("https://ydqc.csu.edu.cn/api/v1/login/oauth2/code/csu?code")
                                    ) {
                                        val cookies = CookieManager.getInstance()
                                            .getCookie("https://ydqc.csu.edu.cn")
                                        Log.d("DEV", "Cookies: $cookies")
                                        //找到cookies中带有token=...的字符串
                                        val tokenPart =
                                            cookies.split(';').find { it.contains("token=") }
                                        //获取等号后面的部分
                                        val token = tokenPart?.split('=')?.get(1)

                                        tokenManager.sendTokenResult(token)
                                        finish()
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

    private fun finish(token: String?) {
        tokenManager.sendTokenResult(token)
        finish()
    }

}