package com.taylorsloan.jobseer.data.coordinate.net.model

/**
 * Model used to parse a geocoding result from Google's API
 * Created by taylorsloan on 11/11/17.
 */
data class GeocodingResponse(var results: List<Result>){

    data class Result(var geometry: Geometry)

    data class Geometry(var location: Location)

    data class Location(var lat: Double, var lng: Double)
}