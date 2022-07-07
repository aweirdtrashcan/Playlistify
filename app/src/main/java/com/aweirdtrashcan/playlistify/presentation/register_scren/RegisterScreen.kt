package com.aweirdtrashcan.playlistify.presentation.register_scren

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aweirdtrashcan.playlistify.presentation.destinations.LoginScreenDestination
import com.aweirdtrashcan.playlistify.presentation.destinations.PlaylistScreenDestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val auth: FirebaseAuth = Firebase.auth
//    if (auth.currentUser != null) {
//        navigator.navigate(LoginScreenDestination())
//    }
    val state = viewModel.state
    val context = LocalContext.current
    val isError by remember { mutableStateOf(state.error) }

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
                value = state.email,
                onValueChange = {
                    RegisterScreenEvents.OnEmailChange(it)
                },
                label = {
                    Text(text = "E-mail")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = state.password,
                onValueChange = {
                    RegisterScreenEvents.OnPasswordChange(it)
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
                val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
                Log.d("firebaseAuth", isEmailValid.toString())
                if (isEmailValid) {
                    viewModel.onEvent(RegisterScreenEvents.Register(state.email, state.password))
                    if (!isError) {
                        navigator.navigate(PlaylistScreenDestination())
                    } else {
                        Toast.makeText(context, "Erro ao cadastrar usuário", Toast.LENGTH_LONG).show()
                    }
                }
            }) {
                Text(text = "Registrar")
            }
        }
    }
}