package com.aweirdtrashcan.playlistify.presentation.playlist_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aweirdtrashcan.playlistify.domain.model.Playlist
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun MainScreen(
    playlists: List<Playlist>,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(playlists.size) { index ->
                    PlaylistCard(
                        playlist = playlists[index],
                        elevation = 5,
                        roundedCornerShape = RoundedCornerShape(10.dp),
                        onClick = {
                            viewModel.onEvent(PlaylistScreenEvents.onClick(playlists[index]))
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
        ) {
            Column {
                AsyncImage(
                    model = playlist.imageUrl,
                    contentDescription = null
                )
            }
            Column {
                Text(
                    text = playlist.name
                )
                Text(
                    text = playlist.user
                )
            }
        }
    }
}