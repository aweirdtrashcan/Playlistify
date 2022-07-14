package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.net.Uri

data class PlaylistAddStates (
    val playlistName: String = "",
    val playlistLink: String = "",
    val playlistPhoto: Uri? = null,
    val playlistUserName: String = "",
    val playlistDescription: String = ""
)