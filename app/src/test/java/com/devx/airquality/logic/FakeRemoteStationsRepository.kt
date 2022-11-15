package com.devx.airquality.logic

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Singleton

@Singleton
class FakeRemoteStationsRepository: RemoteStationsRepository {
    override suspend fun getAll(): List<AirQualityStation> {
       return listOf(AirQualityStation(id = "123", name = "name", city = "city", sponsor = "sponsor", sponsorImage = null))
    }
}