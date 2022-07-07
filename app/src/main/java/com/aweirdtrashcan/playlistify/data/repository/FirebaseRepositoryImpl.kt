package com.aweirdtrashcan.playlistify.data.repository

import com.aweirdtrashcan.playlistify.domain.model.PlaylistUser
import com.aweirdtrashcan.playlistify.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(): FirebaseRepository {
    val firebase: Firebase = Firebase
    private var playlistUser: PlaylistUser? = null
    override fun registerUser(email: String, password: String): Boolean {
        return try {
            firebase.auth.createUserWithEmailAndPassword(email, password)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun logInUser(email: String, password: String): PlaylistUser? {
        firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser: FirebaseUser? = firebase.auth.currentUser
                    playlistUser = PlaylistUser(
                        email = currentUser?.email,
                        isAnonymous = currentUser?.isAnonymous,
                        isEmailVerified = currentUser?.isEmailVerified,
                        displayName = currentUser?.displayName,
                        uid = currentUser?.uid,
                        phoneNumber = currentUser?.phoneNumber,
                        photoUrl = currentUser?.photoUrl
                    )
                }
            }
        return playlistUser
    }
}

