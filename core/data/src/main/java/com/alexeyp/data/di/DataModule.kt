package com.alexeyp.data.di

import com.alexeyp.data.repository.DefaultUserRepository
import com.alexeyp.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsUsersRepository(
        usersRepository: DefaultUserRepository
    ): UserRepository
}