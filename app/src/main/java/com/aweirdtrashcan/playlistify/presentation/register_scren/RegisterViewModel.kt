package com.aweirdtrashcan.playlistify.presentation.register_scren

import android.util.Log
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
class RegisterViewModel @Inject constructor(

): ViewModel() {

    var state by mutableStateOf(RegisterScreenStates())

    private var auth: FirebaseAuth = Firebase.auth;

    fun onEvent(event: RegisterScreenEvents) {
        when(event) {
            is RegisterScreenEvents.Register -> {
                state = state.copy(isLoading = true)
                auth.createUserWithEmailAndPassword(
                    event.email, event.password
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        state = state.copy(error = false)
                        Log.d("firebaseAuth", auth.currentUser.toString())
                    } else {
                        state = state.copy(error = true)
                        Log.d("firebaseAuth", task.exception.toString())
                    }
                }
            }
        }
    }

}