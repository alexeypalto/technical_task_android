package com.alexeyp.data.di

import com.alexeyp.data.repository.DefaultUserRepository
import com.alexeyp.data.repository.UserRepository
import com.alexeyp.data.utils.ConnectivityManagerNetworkMonitor
import com.alexeyp.data.utils.NetworkMonitor
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

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

}