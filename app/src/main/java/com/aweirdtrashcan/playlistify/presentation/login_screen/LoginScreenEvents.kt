package com.aweirdtrashcan.playlistify.presentation.login_screen

sealed class LoginScreenEvents {
    data class OnLoginRequest(val email: String, val password: String): LoginScreenEvents()
    data class OnEmailChange(val email: String): LoginScreenEvents()
    data class OnPasswordChange(val password: String): LoginScreenEvents()
    object OnLoginSuccessful: LoginScreenEvents()
    data class OnLoginError(val errorMessage: String? = null): LoginScreenEvents()
}