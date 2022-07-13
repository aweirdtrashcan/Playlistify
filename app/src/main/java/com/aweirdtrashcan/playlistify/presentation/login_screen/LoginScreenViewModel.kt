package com.aweirdtrashcan.playlistify.presentation.login_screen

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var sharedFlow = MutableSharedFlow<LoginScreenEvents>()
        private set

    var state by mutableStateOf(LoginScreenState())

    fun onEvent(event: LoginScreenEvents) {
        when (event) {
            is LoginScreenEvents.OnLoginRequest -> {
                state = state.copy(isLoading = true)
                auth.signInWithEmailAndPassword(
                    event.email, event.password
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("errortestinggg", "launched: ${it.exception?.message}")

                        state = state.copy(isLoading = false)

                        viewModelScope.launch {
                            sharedFlow.emit(LoginScreenEvents.OnLoginSuccessful)
                        }
                    } else {
                        Log.d("errortestinggg", "launched: ${it.exception?.message}")

                        viewModelScope.launch {
                            sharedFlow.emit(LoginScreenEvents.OnLoginError(it.exception?.message))
                        }

                        state = state.copy(isLoading = false)
                    }
                }
            }
            is LoginScreenEvents.OnEmailChange -> {
                state = state.copy(email = event.email)
            }
            is LoginScreenEvents.OnPasswordChange -> {
                state = state.copy(password = event.password)
            }
            else -> {}
        }
    }

}