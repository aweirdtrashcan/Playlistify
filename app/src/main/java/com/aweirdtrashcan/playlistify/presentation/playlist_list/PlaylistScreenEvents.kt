package com.aweirdtrashcan.playlistify.presentation.playlist_list

import com.aweirdtrashcan.playlistify.domain.model.Playlist

sealed class PlaylistScreenEvents {
    data class OnClick(val playlistItem: Playlist): PlaylistScreenEvents()
}