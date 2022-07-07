package com.aweirdtrashcan.playlistify.presentation.register_screen

data class RegisterScreenStates(
    var email: String = "",
    var password: String = "",
    var isLoading: Boolean = false,
    var error: Boolean = false,

)