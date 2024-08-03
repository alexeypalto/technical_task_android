package com.alexeyp.slidetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexeyp.data.utils.NetworkMonitor
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
        enableEdgeToEdge()
        setContent {
            SlideTestTheme {
                AppUI(networkMonitor)
            }
        }
    }
}