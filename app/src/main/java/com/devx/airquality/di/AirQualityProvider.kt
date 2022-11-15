package com.devx.airquality.di

import com.devx.airquality.data.AirlySDataSource
import com.devx.airquality.data.local.InMemoryStationRepository
import com.devx.airquality.logic.repository.LocalStationRepository
import com.devx.airquality.logic.repository.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
    fun provideLocalStationRepository(): LocalStationRepository {
        return InMemoryStationRepository()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AirAuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlySDataSource.HOST)
            .build()
    }

    @Provides
    @Singleton
    fun provideAirlyService(retrofit: Retrofit): AirlySDataSource.AirlyService {
        return retrofit.create(AirlySDataSource.AirlyService::class.java)
    }

}

class AirAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("apiKey", "1bxSn4EmxywYJCcJ3k0c4WDJNXoePtMF")
        return chain.proceed(requestBuilder.build())
    }
}