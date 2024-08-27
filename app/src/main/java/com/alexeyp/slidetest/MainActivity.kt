package com.alexeyp.slidetest

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexeyp.network.utils.NetworkMonitor
import com.alexeyp.slidetest.ui.AppUI
import com.alexeyp.ui.theme.SlideTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ) { false },
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim,
                darkScrim,
            ) { false },
        )
        setContent {
            SlideTestTheme {
                AppUI(networkMonitor)
            }
        }
    }
}
private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)