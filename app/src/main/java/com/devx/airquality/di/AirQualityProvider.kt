package com.devx.airquality.di

import com.devx.airquality.data.AirlySDataSource
import com.devx.airquality.logic.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {

    @Provides
    @Singleton
    fun provideRemoteStationRepository(airService: AirlySDataSource.AirlyService): RemoteStationsRepository {
        return AirlySDataSource(airService)
    }

    @Provides
    @Singleton
    fun provideAirlyService(): AirlySDataSource.AirlyService{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlySDataSource.HOST)
            .build()
            .create(AirlySDataSource.AirlyService::class.java)
    }

}