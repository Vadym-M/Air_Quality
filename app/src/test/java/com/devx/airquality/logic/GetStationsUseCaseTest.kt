package com.devx.airquality.logic

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.RemoteStationsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetStationsUseCaseTest {

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        val remote = MockRemoteStationRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        Assert.assertEquals(false, remote.isGetAllCalled)
    }

    @Test
    fun executeMakesOneCallToRemote() = runBlocking {
        val remote = MockRemoteStationRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        sut()
        Assert.assertEquals(1, remote.getAllCallsCount)
    }

}

class MockRemoteStationRepository() : RemoteStationsRepository {

    val isGetAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    override suspend fun getAll(): List<AirQualityStation> {
        getAllCallsCount++
        return emptyList()
    }

}