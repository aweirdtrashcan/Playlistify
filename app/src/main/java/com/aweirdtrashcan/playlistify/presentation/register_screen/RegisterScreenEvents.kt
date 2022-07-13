package com.aweirdtrashcan.playlistify.presentation.register_screen

sealed class RegisterScreenEvents {
    data class Register(val email: String, val password: String): RegisterScreenEvents()
    data class OnEmailChange(val email: String): RegisterScreenEvents()
    data class OnPasswordChange(val password: String): RegisterScreenEvents()
    object OnRegisterSuccessful: RegisterScreenEvents()
    data class OnRegisterError(val errorMessage: String?): RegisterScreenEvents()
}