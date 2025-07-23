package com.deiovannagroup.whatsapp_concept.di

import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.MessageRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing Firebase-related dependencies.
 *
 * This module is installed in the SingletonComponent, ensuring that all provided dependencies
 * are singletons and available throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /**
     * Provides a singleton instance of [FirebaseAuthService].
     *
     * @return a singleton [FirebaseAuthService] instance.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuthService()

    /**
     * Provides a singleton instance of [FirebaseFirestoreService].
     *
     * @return a singleton [FirebaseFirestoreService] instance.
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore() = FirebaseFirestoreService()

    /**
     * Provides a singleton instance of [FirebaseStorageService].
     *
     * @return a singleton [FirebaseStorageService] instance.
     */
    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorageService()

    /**
     * Provides a singleton instance of [AuthRepository], injecting [FirebaseAuthService].
     *
     * @return a singleton [AuthRepository] instance.
     */
    @Provides
    @Singleton
    fun provideAuthRepository() = AuthRepository(provideFirebaseAuth())

    /**
     * Provides a singleton instance of [UserRepository], injecting [FirebaseFirestoreService] and [FirebaseStorageService].
     *
     * @return a singleton [UserRepository] instance.
     */
    @Provides
    @Singleton
    fun provideUserRepository() = UserRepository(
        provideFirebaseFirestore(),
        provideFirebaseStorage(),
    )

    /**
     * Provides a singleton instance of [MessageRepository], injecting [FirebaseFirestoreService].
     *
     * @return a singleton [MessageRepository] instance.
     */
    @Provides
    @Singleton
    fun provideChatRepository() = MessageRepository(provideFirebaseFirestore())
}