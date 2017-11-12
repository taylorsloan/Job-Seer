package com.taylorsloan.jobseer.data.repo.sources.coordinates

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.service.GeocodingService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by taylorsloan on 11/11/17.
 */
class CloudDataSource(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var geocodingService : GeocodingService

    init {
        dataModule.netComponent().inject(this)
    }

    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return geocodingService.getCoordinatesFromAddress(address,
                DataModuleImpl.GEOCODING_API_KEY)
                .map {
                    val location = it.results[0].geometry.location
                    Pair(location.lat, location.lng)
                }
    }
}