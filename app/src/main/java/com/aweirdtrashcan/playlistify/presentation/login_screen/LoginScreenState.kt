package com.aweirdtrashcan.playlistify.presentation.login_screen

import com.google.firebase.auth.FirebaseUser

data class LoginScreenState(
    var onError: Boolean = false,
    var userInfo: FirebaseUser? = null
)