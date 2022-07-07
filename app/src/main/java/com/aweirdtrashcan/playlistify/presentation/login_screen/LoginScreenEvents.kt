package com.aweirdtrashcan.playlistify.presentation.login_screen

sealed class LoginScreenEvents {
    data class onLoginRequest(val email: String, val password: String): LoginScreenEvents()
}