package com.aweirdtrashcan.playlistify.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    var state by mutableStateOf(LoginScreenState())

    fun onEvent(event: LoginScreenEvents) {
        when(event) {
            is LoginScreenEvents.onLoginRequest -> {
                auth.signInWithEmailAndPassword(
                    event.email, event.password
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        state = state.copy(userInfo = auth.currentUser, onError = false)
                    } else {
                        state = state.copy(onError = true)
                    }
                }
            }
        }
    }

}