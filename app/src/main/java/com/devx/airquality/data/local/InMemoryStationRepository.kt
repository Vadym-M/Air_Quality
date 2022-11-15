package com.devx.airquality.data.local

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.LocalStationRepository

class InMemoryStationRepository : LocalStationRepository {
    override suspend fun getAll(): List<AirQualityStation> {
        TODO("Not yet implemented")
    }

    override suspend fun save(list: List<AirQualityStation>) {
        TODO("Not yet implemented")
    }
}