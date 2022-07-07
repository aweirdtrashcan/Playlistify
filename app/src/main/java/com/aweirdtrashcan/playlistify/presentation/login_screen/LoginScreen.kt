package com.aweirdtrashcan.playlistify.presentation.login_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.aweirdtrashcan.playlistify.presentation.destinations.PlaylistScreenDestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val auth: FirebaseAuth = Firebase.auth
    if (auth.currentUser != null) {
        navigator.navigate(PlaylistScreenDestination())
    }
}