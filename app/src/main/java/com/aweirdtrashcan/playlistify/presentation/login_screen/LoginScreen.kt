package com.aweirdtrashcan.playlistify.presentation.login_screen

import android.util.Log
import android.util.Patterns
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
import com.aweirdtrashcan.playlistify.presentation.destinations.PlaylistScreenDestination
import com.aweirdtrashcan.playlistify.presentation.destinations.RegisterScreenDestination
import com.aweirdtrashcan.playlistify.presentation.util.ErrorComposable
import com.aweirdtrashcan.playlistify.presentation.util.isEmailAndPasswordValid
import com.aweirdtrashcan.playlistify.util.Constants.ON_LOGIN_ERROR
import com.aweirdtrashcan.playlistify.util.Constants.ON_LOGIN_SUCCESS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@Destination(start = true)
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    var shouldShowErrorComposable by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }
    if (Firebase.auth.currentUser != null) {
        navigator.navigate(PlaylistScreenDestination())
    }

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collect {
            when(it) {
                is LoginScreenEvents.OnLoginSuccessful -> {
                    navigator.navigate(PlaylistScreenDestination())
                }
                is LoginScreenEvents.OnLoginError -> {
                    coroutine.launch {
                        errorMessage = "Erro ao logar usuário: ${it.errorMessage}"
                        shouldShowErrorComposable = true
                        delay(5000L)
                        shouldShowErrorComposable = false
                        errorMessage = ""
                    }
                }
                else -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight(.60f)
                .fillMaxWidth(.95f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Para continuar, Logue-se abaixo!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvents.OnEmailChange(it))
                },
                label = {
                    Text(text = "E-mail")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvents.OnPasswordChange(it))
                },
                label = {
                    Text(text = "Senha")
                }
            )
            ErrorComposable(error = errorMessage, state = shouldShowErrorComposable)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Esqueci minha senha",
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                if (isEmailAndPasswordValid(state.email, state.password)) {
                    viewModel.onEvent(LoginScreenEvents.OnLoginRequest(state.email, state.password))
                } else {
                    coroutine.launch {
                        errorMessage = "Email ou Senha inválidos."
                        shouldShowErrorComposable = true
                        delay(5000L)
                        shouldShowErrorComposable = false
                        errorMessage = ""
                    }
                }
            }) {
                Text(text = "Logar")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Não é cadastrado ainda?",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                navigator.navigate(RegisterScreenDestination())
            }) {
                Text(
                    text = "Cadastre-se"
                )
            }
        }
    }
}