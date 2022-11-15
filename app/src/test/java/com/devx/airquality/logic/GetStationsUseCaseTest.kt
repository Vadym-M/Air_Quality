package com.devx.airquality.logic

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.LocalStationRepository
import com.devx.airquality.logic.repository.RemoteStationsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetStationsUseCaseTest {
    //SUT system under test
    lateinit var sut: GetStationsUseCase
    lateinit var remote: MockRemoteStationRepository
    lateinit var local: MockLocalStationRepository

    @Before
    fun setUp() {
        local = MockLocalStationRepository()
        remote = MockRemoteStationRepository()
        sut = GetStationsUseCase(remoteStationsRepository = remote, localStationsRepository = local)

    }

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        val remote = MockRemoteStationRepository()
        Assert.assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneToLocal() = runBlocking {
        sut()
        Assert.assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeMakesCallToRemoteWhenLocalDataIsEmpty() = runBlocking {
        local.getAllResult = emptyList()

        sut()

        Assert.assertEquals(true, remote.getAllCalled)
    }

    @Test
    fun executeDoesNotMakeCallToRemoteWhenLocalDataIsNotEmpty() = runBlocking {
        local.getAllResult = listOf(sampleAQStation)

        sut()

        Assert.assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeDoesMakeOneCallToLocal() = runBlocking {
        sut()
        Assert.assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeDoesMakeOneCallToLocalForNonEmptyData() = runBlocking {
        local.getAllResult = listOf(sampleAQStation)

        sut()

        Assert.assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeReturnsRemoteStationsWhenRemoteStationRepositoryIsCalled() = runBlocking {
        local.getAllResult = emptyList()
        remote.getAllResult = listOf(sampleAQStation)

        val actual = sut()

        Assert.assertEquals("1", actual.first().id)

    }

    @Test
    fun executeReturnsLocalStationsWhenLocalStationRepositoryIsCalled() = runBlocking {
        local.getAllResult = listOf(sampleAQStation)

        val actual = sut()

        Assert.assertEquals("1", actual.first().id)

    }

    @Test
    fun executeSavesStationsToLocalWhenRemoteIsNonEmpty() = runBlocking {
        local.getAllResult = emptyList()
        remote.getAllResult = listOf(sampleAQStation)

        sut()

        Assert.assertEquals(true, local.saveCalled)
        Assert.assertEquals("1", local.saveReceivedArguments.first().id)
    }

    @Test
    fun executeReturnsValidLocalListStations() = runBlocking {
        val sampleAQStation2 = AirQualityStation("2", "", "", "", "")

        local.getAllResult = listOf(sampleAQStation, sampleAQStation2)

        val actual = sut()

        Assert.assertEquals("1", actual.first().id)
        Assert.assertEquals("2", actual[1].id)
    }

    var sampleAQStation = AirQualityStation("1", "", "", "", "")


}

class MockLocalStationRepository() : LocalStationRepository {

    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    val saveCalled: Boolean
        get() = saveCallsCount > 0
    var saveCallsCount: Int = 0

    var getAllResult: List<AirQualityStation> = emptyList()

    var saveReceivedArguments: List<AirQualityStation> = emptyList()

    override suspend fun getAll(): List<AirQualityStation> {
        getAllCallsCount++
        return getAllResult
    }

    override suspend fun save(list: List<AirQualityStation>) {
        saveReceivedArguments = list
        saveCallsCount++
    }


}

class MockRemoteStationRepository() : RemoteStationsRepository {

    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    var getAllResult: List<AirQualityStation> = emptyList()

    override suspend fun getAll(): List<AirQualityStation> {
        getAllCallsCount++
        return getAllResult
    }

}