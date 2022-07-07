package com.aweirdtrashcan.playlistify.di

import com.aweirdtrashcan.playlistify.data.repository.FirebaseRepositoryImpl
import com.aweirdtrashcan.playlistify.domain.repository.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideFirebasebaseRepositoryImpl(
        firebaseRepositoryImpl: FirebaseRepositoryImpl
    ): FirebaseRepository

}