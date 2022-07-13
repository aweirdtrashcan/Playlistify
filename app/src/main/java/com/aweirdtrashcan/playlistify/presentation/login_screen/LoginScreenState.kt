package com.aweirdtrashcan.playlistify.presentation.login_screen

data class LoginScreenState(
    var email: String = "",
    var password: String = "",
    var isLoading: Boolean = false
)