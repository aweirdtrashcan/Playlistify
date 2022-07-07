package com.aweirdtrashcan.playlistify.presentation.playlist_list

import androidx.lifecycle.ViewModel
import com.aweirdtrashcan.playlistify.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    firebaseRepository: FirebaseRepository
): ViewModel() {

    fun onEvent(event: PlaylistScreenEvents) {
        when(event) {
            is PlaylistScreenEvents.onClick -> {
                event.playlistItem
            }
        }
    }

}