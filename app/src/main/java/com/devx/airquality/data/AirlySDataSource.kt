package com.devx.airquality.data

import com.devx.airquality.entity.AirQualityStation
import com.devx.airquality.logic.repository.RemoteStationsRepository
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import javax.inject.Inject

class AirlySDataSource @Inject constructor(private val airlyService: AirlyService):
    RemoteStationsRepository {
    override suspend fun getAll(): List<AirQualityStation> {
        return airlyService.getInstallations().map {
            AirQualityStation(
                id = it.id.toString(),
                name = it.address?.displayAddress2 ?: "Unknown",
                city = it.address?.city ?: "Unknown",
                sponsor =  it.sponsor?.displayName ?: "Unknown",
                sponsorImage = it.sponsor?.logo ?: "Unknown"
            )
        }
    }

    companion object {
        const val HOST = "https://airapi.airly.eu/v2/"
    }

    interface AirlyService {
        @GET("installations/nearest?lat=50.062006&lng=19.940984&maxDistanceKM=5&maxResults=100")
        suspend fun getInstallations(): List<Installation>
    }

    data class Installation (

        @SerializedName("id") var id: Int?,
        @SerializedName("location") var location: Location? = Location(),
        @SerializedName("locationId") var locationId: Int? = null,
        @SerializedName("address") var address: Address? = Address(),
        @SerializedName("elevation") var elevation: Double? = null,
        @SerializedName("airly") var airly: Boolean? = null,
        @SerializedName("sponsor") var sponsor: Sponsor? = Sponsor()

    )

    data class Location(

        @SerializedName("latitude") var latitude: Double? = null,
        @SerializedName("longitude") var longitude: Double? = null

    )

    data class Address(

        @SerializedName("country") var country: String? = null,
        @SerializedName("city") var city: String? = null,
        @SerializedName("street") var street: String? = null,
        @SerializedName("number") var number: String? = null,
        @SerializedName("displayAddress1") var displayAddress1: String? = null,
        @SerializedName("displayAddress2") var displayAddress2: String? = null

    )


    data class Sponsor(

        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("logo") var logo: String? = null,
        @SerializedName("link") var link: String? = null,
        @SerializedName("displayName") var displayName: String? = null

    )
}