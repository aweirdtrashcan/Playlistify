package com.aweirdtrashcan.playlistify.presentation.playlist_add_screen

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toIcon
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aweirdtrashcan.playlistify.MainActivity
import com.aweirdtrashcan.playlistify.domain.model.Playlist
import com.aweirdtrashcan.playlistify.util.Constants
import com.aweirdtrashcan.playlistify.util.Constants.PLAYLIST_CHILD
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PlaylistAddViewModel @Inject constructor(
    app: Application
): ViewModel() {

    companion object {
        const val URL_REFERENCE: String = "gs://playlistify-70fae.appspot.com/playlist-images"
    }

    var state by mutableStateOf(PlaylistAddStates())

    var sharedFlow = MutableSharedFlow<PlaylistAddEvents>()
        private set

    private val mStorage: StorageReference = Firebase.storage.getReferenceFromUrl(URL_REFERENCE)

    private var uri: Uri? = null

    private val db: DatabaseReference = Firebase.database.getReference("playlists")

    fun onEvent(events: PlaylistAddEvents) {
        when(events) {
            is PlaylistAddEvents.OnPlaylistSend -> {
                try {
                    mStorage.putFile(events.playlist.imageUri!!).addOnSuccessListener {
                        events.playlist.imageUri = it.uploadSessionUri
                        sendPlaylist(events.playlist) ?: throw IOException("Error Uploading Photo")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    viewModelScope.launch {
                        sharedFlow.emit(PlaylistAddEvents.OnPlaylistSentError(e.message.toString()))
                    }
                }
            }
            else -> {}
        }
    }

    private fun sendPlaylist(playlist: Playlist): Exception? {
        return try {
            db.child(PLAYLIST_CHILD).push().setValue(playlist)
            viewModelScope.launch {
                sharedFlow.emit(PlaylistAddEvents.OnPlaylistSentSuccessfully)
            }
            null
        } catch (e: Exception) {
            viewModelScope.launch {
                sharedFlow.emit(PlaylistAddEvents.OnPlaylistSentError(e.message.toString()))
            }
            e
        }
    }
}