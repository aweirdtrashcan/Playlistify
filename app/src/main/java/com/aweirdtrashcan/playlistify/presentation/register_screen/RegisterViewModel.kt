package com.aweirdtrashcan.playlistify.presentation.register_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aweirdtrashcan.playlistify.util.Constants
import com.aweirdtrashcan.playlistify.util.Constants.ON_REGISTER_ERROR
import com.aweirdtrashcan.playlistify.util.Constants.ON_REGISTER_SUCCESS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(RegisterScreenStates())
    var sharedFlow = MutableSharedFlow<RegisterScreenEvents>()
        private set

    private var auth: FirebaseAuth = Firebase.auth;

    fun onEvent(event: RegisterScreenEvents) {
        when(event) {
            is RegisterScreenEvents.Register -> {
                state = state.copy(isLoading = true)
                auth.createUserWithEmailAndPassword(
                    event.email, event.password
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        state = state.copy(isLoading = false)

                        viewModelScope.launch {
                            sharedFlow.emit(RegisterScreenEvents.OnRegisterSuccessful)
                        }

                        Log.d("firebaseAuth", auth.currentUser.toString())
                    } else {
                        state = state.copy(isLoading = false)

                        viewModelScope.launch {
                            sharedFlow.emit(RegisterScreenEvents.OnRegisterError(task.exception?.message))
                        }

                        Log.d("firebaseAuth", task.exception.toString())
                    }
                }
            }
            is RegisterScreenEvents.OnEmailChange -> {
                state = state.copy(email = event.email)
            }
            is RegisterScreenEvents.OnPasswordChange -> {
                state = state.copy(password = event.password)
            }
            else -> {}
        }
    }

}