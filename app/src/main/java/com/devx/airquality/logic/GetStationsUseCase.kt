package com.devx.airquality.logic

import com.devx.airquality.entity.AirQualityStation
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val remoteStationsRepository: RemoteStationsRepository
) {
    suspend operator fun invoke(): List<AirQualityStation> {
        return remoteStationsRepository.getAll()
    }
}