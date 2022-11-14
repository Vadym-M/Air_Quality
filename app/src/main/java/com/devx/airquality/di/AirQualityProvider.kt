package com.devx.airquality.di

import com.devx.airquality.logic.FakeRemoteStationsRepository
import com.devx.airquality.logic.GetStationsUseCase
import com.devx.airquality.logic.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {

    @Provides
    fun provideStationsProvider(remoteStationsRepository: RemoteStationsRepository): GetStationsUseCase {
        return GetStationsUseCase(remoteStationsRepository)
    }

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(): RemoteStationsRepository {
        return FakeRemoteStationsRepository()
    }

}