package com.aweirdtrashcan.playlistify.presentation.playlist_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.snap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aweirdtrashcan.playlistify.domain.model.Playlist
import com.aweirdtrashcan.playlistify.util.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(PlaylistScreenState())
        private set
    private val dbInstance: DatabaseReference = Firebase.database.getReference("playlists")
    private val playlists = mutableListOf<Playlist>()

    init {
        getAllPlaylists()
    }

    fun onEvent(event: PlaylistScreenEvents) {
        when(event) {
            is PlaylistScreenEvents.OnClick -> {

            }
        }
    }

    private fun getAllPlaylists() {
        Log.d("playlists", "Oláaaaaaaaaaaaaaaaaa")
        dbInstance.child(Constants.PLAYLIST_CHILD).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                playlists.clear()
                for (i in snapshot.children) {
                    val playlist: Playlist? = i.getValue(Playlist::class.java)
                    playlists.add(playlist!!)
                }
                if (state.playlists.isEmpty()) {
                    state = state.copy(playlists = playlists)
                } else {
                    state = state.copy(playlists = emptyList<Playlist>().toMutableList())
                    state = state.copy(playlists = playlists)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun insertPlaylist(playlist: Playlist): String {
        return try {
            dbInstance.child(Constants.PLAYLIST_CHILD).push().setValue(playlist)
            "Playlist Enviada com Sucesso!"
        } catch (e: Exception) {
            e.printStackTrace()
            "Erro: $e"
        }

    }

}