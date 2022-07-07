package com.aweirdtrashcan.playlistify.domain.model

import android.net.Uri

data class PlaylistUser(
    var email: String?,
    var isAnonymous: Boolean?,
    var isEmailVerified: Boolean?,
    var displayName: String?,
    var uid: String?,
    var phoneNumber: String?,
    var photoUrl: Uri?
)