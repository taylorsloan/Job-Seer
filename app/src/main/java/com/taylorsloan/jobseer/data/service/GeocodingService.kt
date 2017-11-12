package com.taylorsloan.jobseer.data.service

import com.taylorsloan.jobseer.data.model.GeocodingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by taylorsloan on 11/11/17.
 */
interface GeocodingService {

    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    fun getCoordinatesFromAddress(@Query("address") address: String,
                                  @Query("key") apiKey : String) : Single<GeocodingResponse>
}