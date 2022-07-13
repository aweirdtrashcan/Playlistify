package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.net.Uri
import com.aweirdtrashcan.playlistify.domain.model.Playlist

sealed class PlaylistAddEvents {
    object OnPlaylistSentSuccessfully: PlaylistAddEvents()
    data class OnPlaylistSentError(val error: String): PlaylistAddEvents()
    data class OnPlaylistSend(val playlist: Playlist): PlaylistAddEvents()
    data class OnPlaylistNameChanged(val playlistName: String): PlaylistAddEvents()
    data class OnPlaylistAuthorChanged(val playlistAuthor: String): PlaylistAddEvents()
    data class OnPlaylistImageChanged(val playlistImage: Uri): PlaylistAddEvents()
    data class OnPlaylistUrlChanged(val Url: String): PlaylistAddEvents()
    data class OnPlaylistDescriptionChanged(val description: String): PlaylistAddEvents()

}