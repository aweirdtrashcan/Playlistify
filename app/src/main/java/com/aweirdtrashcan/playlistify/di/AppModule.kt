package com.aweirdtrashcan.playlistify.di

import com.aweirdtrashcan.playlistify.presentation.login_screen.LoginScreenViewModel
import com.aweirdtrashcan.playlistify.presentation.playlist_list.PlaylistViewModel
import com.aweirdtrashcan.playlistify.presentation.register_screen.RegisterViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegisterViewModel(): RegisterViewModel {
        return RegisterViewModel()
    }

    @Provides
    @Singleton
    fun providePlaylistViewModel(): PlaylistViewModel {
        return PlaylistViewModel()
    }

    @Provides
    @Singleton
    fun privateLoginViewModel(): LoginScreenViewModel {
        return LoginScreenViewModel()
    }
}