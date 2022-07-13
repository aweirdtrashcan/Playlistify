package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.aweirdtrashcan.playlistify.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
fun PlaylistAddScreen(
    navigator: DestinationsNavigator,
    viewModel: PlaylistAddViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var lightImage: Any = painterResource(id = R.drawable.ic_image_icon_light)
    var darkImage: Any = painterResource(id = R.drawable.ic_image_icon_dark)

    val packageName: String = "com.aweirdtrashcan.playlistify"
    val uri: Uri = if (isSystemInDarkTheme()) {
        Uri.parse("android.resources://$packageName/drawable/ic_image_icon_dark")
    } else {
        Uri.parse("android.resources://$packageName/drawable/ic_image_icon_light")
    }

    viewModel.onEvent(PlaylistAddEvents.OnPlaylistImageChanged(
        uri
    ))

    val coroutine = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { image ->
            coroutine.launch {
                val lUri = (image.data?.data as Uri)
                viewModel.onEvent(PlaylistAddEvents.OnPlaylistImageChanged(lUri))
            }
        }
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Spacer(modifier = Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = state.playlistName!!,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            TextField(
                value = state.playlistName!!,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistNameChanged(it))
                },
                label = {
                    Text(text = "Nome da Playlist")
                }
            )
            TextField(
                value = state.playlistDescription!!,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistDescriptionChanged(it))
                },
                label = {
                    Text(text = "Descrição para Playlist")
                }
            )
            Image(
                painter =
                rememberAsyncImagePainter(uri),
                contentDescription = null,
                Modifier.clickable {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    launcher.launch(intent)
                }
            )
            TextField(
                value = state.playlistUserName!!,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistAuthorChanged(it))
                },
                label = {
                    Text(text = "Nome de usuário (nickname)")
                }
            )
            TextField(
                value = state.playlistUserName!!,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistAuthorChanged(it))
                },
                label = {
                    Text(text = "Nome de usuário (nickname)")
                }
            )
        }
    }
}