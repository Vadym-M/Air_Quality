package com.devx.airquality.logic.repository

import com.devx.airquality.entity.AirQualityStation

interface LocalStationRepository {
    suspend fun  getAll() : List<AirQualityStation>
    suspend fun save(list: List<AirQualityStation>)
}
