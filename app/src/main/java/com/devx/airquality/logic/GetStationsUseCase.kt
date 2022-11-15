package com.devx.airquality.logic

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.LocalStationRepository
import com.devx.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val remoteStationsRepository: RemoteStationsRepository,
    private val localStationsRepository: LocalStationRepository
) {
    suspend operator fun invoke(): List<AirQualityStation> {
        val localStations = localStationsRepository.getAll()
        if(localStations.isEmpty()){
            val remoteStations =  remoteStationsRepository.getAll()
            localStationsRepository.save(remoteStations)
            return remoteStations
        }
        return localStations
    }
}