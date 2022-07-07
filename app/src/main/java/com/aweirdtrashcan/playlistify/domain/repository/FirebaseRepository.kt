package com.aweirdtrashcan.playlistify.domain.repository

import com.aweirdtrashcan.playlistify.domain.model.PlaylistUser


interface FirebaseRepository {
    fun registerUser(email: String, password: String): Boolean
    fun logInUser(email: String, password: String): PlaylistUser?
}