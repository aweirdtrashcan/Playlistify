package com.aweirdtrashcan.playlistify.presentation.playlist_list

import com.aweirdtrashcan.playlistify.domain.model.Playlist

data class PlaylistScreenState(
    val playlists: MutableList<Playlist> = mutableListOf()
)