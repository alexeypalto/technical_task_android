package com.alexeyp.network.di

import com.alexeyp.network.NetworkDataSource
import com.alexeyp.network.retrofit.RetrofitNetwork
import com.alexeyp.network.utils.ConnectivityManagerNetworkMonitor
import com.alexeyp.network.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreNetworkModule {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor


    @Binds
    fun binds(impl: RetrofitNetwork): NetworkDataSource
}