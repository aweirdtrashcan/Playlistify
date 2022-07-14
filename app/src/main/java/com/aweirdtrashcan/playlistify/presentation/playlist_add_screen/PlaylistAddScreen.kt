package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.aweirdtrashcan.playlistify.R
import com.aweirdtrashcan.playlistify.domain.model.Playlist
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

    val context = LocalContext.current

    val mImage = remember {
        mutableStateOf(
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(context.resources.getResourcePackageName(R.drawable.ic_add_image_icon))
                .appendPath(context.resources.getResourceTypeName(R.drawable.ic_add_image_icon))
                .appendPath(context.resources.getResourceEntryName(R.drawable.ic_add_image_icon))
                .build()
        )
    }


    val coroutine = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { image ->
        coroutine.launch {
            val lUri: Uri? = image
            lUri?.let {
                mImage.value = it
            }
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = state.playlistName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            TextField(
                value = state.playlistName,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistNameChanged(it))
                },
                label = {
                    Text(text = "Nome da Playlist")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = state.playlistDescription,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistDescriptionChanged(it))
                },
                label = {
                    Text(text = "Descrição para Playlist")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter =
                rememberAsyncImagePainter(mImage.value),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                    }
                    .size(150.dp)
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .align(CenterHorizontally)
                    .clip(shape = RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = state.playlistUserName,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistAuthorChanged(it))
                },
                label = {
                    Text(text = "Nome de usuário (nickname)")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = state.playlistLink,
                onValueChange = {
                    viewModel.onEvent(PlaylistAddEvents.OnPlaylistUrlChanged(it))
                },
                label = {
                    Text(text = "Link da Playlist")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                onClick = {
                    val isNameNotEmpty = state.playlistName.isNotEmpty()
                    val isDescriptionNotEmpty = state.playlistDescription.isNotEmpty()
                    val isUserNameNotEmpty = state.playlistUserName.isNotEmpty()
                    val isLinkSpotify = if (state.playlistLink.contains("https://open.spotify.com/playlist/")) state.playlistLink else ""
                    val isNameNotEmptyAndIsValid = isLinkSpotify.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(state.playlistLink).matches()
                    val isImageNotTheStandard = mImage.value.toString() != "android.resource://com.aweirdtrashcan.playlistify/drawable/ic_add_image_icon"
                    if (isNameNotEmpty &&
                        isDescriptionNotEmpty &&
                        isUserNameNotEmpty &&
                        isNameNotEmptyAndIsValid &&
                        isImageNotTheStandard
                    ) {
                        viewModel.onEvent(PlaylistAddEvents.OnPlaylistSend(
                            Playlist(
                                name = state.playlistName,
                                spotifyUrl = isLinkSpotify,
                                user = state.playlistUserName,
                                description = state.playlistDescription,
                                imageUri = mImage.value
                            )
                        ))
                    }
                }) {
                Text(
                    text = "Enviar",
                )
            }
        }
    }
}