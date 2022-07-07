package com.aweirdtrashcan.playlistify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aweirdtrashcan.playlistify.presentation.NavGraphs
import com.aweirdtrashcan.playlistify.ui.theme.PlaylistifyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistifyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

