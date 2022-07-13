package com.aweirdtrashcan.playlistify.presentation.playlist_list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toIcon
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aweirdtrashcan.playlistify.domain.model.Playlist
import com.aweirdtrashcan.playlistify.presentation.destinations.PlaylistInfoScreenDestination
import com.aweirdtrashcan.playlistify.presentation.destinations.PlaylistScreenDestination
import com.aweirdtrashcan.playlistify.presentation.playlist_info_screen.PlaylistInfoScreen
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun PlaylistScreen(
    navigator: DestinationsNavigator,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val user: FirebaseUser? = Firebase.auth.currentUser
    val state: PlaylistScreenState = viewModel.state
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                items(state.playlists) { item ->
                    PlaylistCard(
                        modifier = Modifier.padding(5.dp),
                        playlist = item,
                        elevation = 5,
                        roundedCornerShape = RoundedCornerShape(10.dp),
                        onClick = {
                            navigator.navigate(PlaylistInfoScreenDestination(it))
                        }
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistCard(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    elevation: Int,
    roundedCornerShape: RoundedCornerShape,
    onClick: (playlist: Playlist) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = elevation.dp,
        shape = roundedCornerShape,
        onClick = { onClick(playlist) }
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column {
                playlist.imageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier.clip(shape = RoundedCornerShape((3.5).dp))
                    )
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                playlist.name?.let {
                    Text(text = it)
                }
                playlist.name?.let {
                    Text(text = it)
                }
            }
        }
    }
}