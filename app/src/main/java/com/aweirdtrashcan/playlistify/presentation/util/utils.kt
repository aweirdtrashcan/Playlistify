package com.aweirdtrashcan.playlistify.presentation.util

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ErrorComposable(
    error: String,
    state: Boolean
) {
    AnimatedVisibility(visible = state) {
        Text(
            text = error,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

fun isEmailAndPasswordValid(email: String, password: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
}