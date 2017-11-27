package com.taylorsloan.jobseer.data.coordinate.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.coordinate.net.service.GeocodingService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of a latlong data source using Google's API
 * Created by taylorsloan on 11/11/17.
 */
class CloudDataSource(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var geocodingService : GeocodingService

    init {
        dataModule.netComponent().inject(this)
    }

    /**
     * Get coordinates for an address using the network
     * @param address The address to be geocoded
     */
    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return geocodingService.getCoordinatesFromAddress(address,
                DataModuleImpl.GEOCODING_API_KEY)
                .map {
                    val location = it.results[0].geometry.location
                    Pair(location.lat, location.lng)
                }
    }
}