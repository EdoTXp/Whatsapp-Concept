package com.deiovannagroup.whatsapp_concept.di

import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.ChatRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuthService()


    @Provides
    @Singleton
    fun provideFirebaseFirestore() = FirebaseFirestoreService()


    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorageService()

    @Provides
    @Singleton
    fun provideAuthRepository() = AuthRepository(provideFirebaseAuth())

    @Provides
    @Singleton
    fun provideUserRepository() = UserRepository(
        provideFirebaseFirestore(),
        provideFirebaseStorage(),
    )

    @Provides
    @Singleton
    fun provideChatRepository() = ChatRepository(provideFirebaseFirestore())


}