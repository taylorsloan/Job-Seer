package com.taylorsloan.jobseer.data.coordinate.net.service

import com.taylorsloan.jobseer.data.coordinate.net.model.GeocodingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service that defines how to get latitude and longitude from an address using Google
 * Created by taylorsloan on 11/11/17.
 */
interface GeocodingService {

    /**
     * Uses Googles API to get coordinates from there geocoding API
     * @param address The address to be geocoded
     * @param apiKey The API key used to make the request
     */
    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    fun getCoordinatesFromAddress(@Query("address") address: String,
                                  @Query("key") apiKey : String) : Single<GeocodingResponse>
}