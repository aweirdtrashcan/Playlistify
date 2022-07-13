package com.aweirdtrashcan.playlistify.presentation.register_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.aweirdtrashcan.playlistify.presentation.util.ErrorComposable
import com.aweirdtrashcan.playlistify.presentation.util.isEmailAndPasswordValid
import com.aweirdtrashcan.playlistify.ui.theme.PlaylistifyTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var shouldShowErrorComposable by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collect {
            when(it) {
                is RegisterScreenEvents.OnRegisterSuccessful -> {
                    navigator.navigate(PlaylistScreenDestination())
                }
                is RegisterScreenEvents.OnRegisterError -> {
                    shouldShowErrorComposable = true
                    errorMessage = it.errorMessage!!
                    delay(5000L)
                    errorMessage = ""
                    shouldShowErrorComposable = false
                }
                else -> {}
            }
        }
    }

    PlaylistifyTheme {
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
                    text = "Bem vindo ao Playlistify!\nPara começar, registre-se:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = state.email,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvents.OnEmailChange(it))
                    },
                    label = {
                        Text(text = "E-mail")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvents.OnPasswordChange(it))
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
                        viewModel.onEvent(RegisterScreenEvents.Register(state.email, state.password))
                    } else {
                        coroutine.launch {
                            errorMessage = "Por favor, digite um email ou senha válidos."
                            shouldShowErrorComposable = true
                            delay(5000L)
                            errorMessage = ""
                            shouldShowErrorComposable = false
                        }
                    }
                }) {
                    Text(text = "Registrar")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Já cadastrado?",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    navigator.navigate(LoginScreenDestination())
                }) {
                    Text(
                        text = "Login"
                    )
                }
            }
        }
    }
}