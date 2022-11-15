package com.devx.airquality.logic.repository

import com.devx.airquality.entity.AirQualityStation

interface RemoteStationsRepository {
   suspend fun getAll(): List<AirQualityStation>
}