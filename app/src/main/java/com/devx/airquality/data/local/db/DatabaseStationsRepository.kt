package com.devx.airquality.data.local.db

import androidx.room.*
import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.LocalStationRepository
import javax.inject.Inject

class DatabaseStationsRepository @Inject constructor(private val database: AppDatabase) :
    LocalStationRepository {
    override suspend fun getAll(): List<AirQualityStation> {
        val stationEntities = database.stationsDao().getAll()
        return stationEntities.map {
            AirQualityStation(
                it.id,
                it.name,
                it.city,
                it.sponsor,
                it.sponsorImage
            )
        }
    }

    override suspend fun save(list: List<AirQualityStation>) {
       database.stationsDao().insert(list.map {
           StationEntity(
                 it.id,
                 it.name,
                 it.city,
                 it.sponsor,
                 it.sponsorImage
           )
       })
    }
}

@Entity
data class StationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "sponsor") val sponsor: String,
    @ColumnInfo(name = "sponsor_image") val sponsorImage: String?
)

@Dao
interface StationsDao {
    @Query("SELECT * FROM StationEntity")
    suspend fun getAll(): List<StationEntity>

    @Insert
    suspend fun insert(list: List<StationEntity>)
}

@Database(entities = [StationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationsDao(): StationsDao
}