package com.aweirdtrashcan.playlistify.domain.model

data class Playlist(
    val id: Int,
    val name: String,
    val user: String,
    val imageUrl: String? = null,
    val songsAmount: Int,
    val spotifyUrl: String
)
