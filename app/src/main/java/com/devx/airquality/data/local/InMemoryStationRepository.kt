package com.devx.airquality.data.local

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.LocalStationRepository

class InMemoryStationRepository : LocalStationRepository {

    companion object {
        private var stations: List<AirQualityStation> = emptyList()
    }

    override suspend fun getAll(): List<AirQualityStation> {
        return stations
    }

    override suspend fun save(list: List<AirQualityStation>) {
        InMemoryStationRepository.stations = stations
    }
}