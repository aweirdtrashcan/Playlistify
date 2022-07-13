package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.net.Uri

data class PlaylistAddStates (
    val playlistName: String? = null,
    val playlistLink: String? = null,
    val playlistPhoto: Uri? = null,
    val playlistUserName: String? = null,
    val playlistDescription: String? = null
)