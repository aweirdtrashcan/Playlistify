package com.aweirdtrashcan.playlistify.presentation.register_scren

sealed class RegisterScreenEvents {
    data class Register(val email: String, val password: String): RegisterScreenEvents()
}