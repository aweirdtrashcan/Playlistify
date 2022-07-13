package com.aweirdtrashcan.playlistify.domain.model

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Playlist(
    var name: String? = null,
    var user: String? = null,
    var imageUri: Uri? = null,
    var spotifyUrl: String? = null,
    var description: String? = null
): Parcelable
