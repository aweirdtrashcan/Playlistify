package com.aweirdtrashcan.playlistify.presentation.register_scren

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aweirdtrashcan.playlistify.presentation.register_scren.RegisterScreenEvents
import com.aweirdtrashcan.playlistify.presentation.register_scren.RegisterViewModel
import com.aweirdtrashcan.playlistify.presentation.screens.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state = viewModel.state
    var email by remember {
        mutableStateOf(state.email)
    }
    var pass by remember {
        mutableStateOf(state.password)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxHeight(.45f)
                .fillMaxWidth(.95f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Bem vindo ao Playlistify!\nPara começar, registre-se:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = "E-mail")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = pass,
                onValueChange = {
                    pass = it
                },
                label = {
                    Text(text = "Senha")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Esqueci minha senha",
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                Log.d("firebaseAuth", isEmailValid.toString())
                if (isEmailValid) {
                    viewModel.onEvent(RegisterScreenEvents.Register(email, pass))
                    if (!state.error) {
                        navigator.navigate(MainScreenDestination)
                    } else {
                        //TODO: Error Dialog
                    }
                }
            }) {
                Text(text = "Registrar")
            }
        }
    }
}