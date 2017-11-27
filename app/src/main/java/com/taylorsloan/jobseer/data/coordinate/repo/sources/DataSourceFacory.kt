package com.taylorsloan.jobseer.data.coordinate.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import io.reactivex.Single

/**
 * Wrapper for latlong data sources
 * Created by taylorsloan on 11/11/17.
 */
class DataSourceFacory(dataModule: DataModule) : DataSource {

    private val cloudDataSource = CloudDataSource(dataModule)

    /**
     * Gets coordinates using an address
     * @param address The address to get coordinates for
     */
    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return cloudDataSource.getCoordinatesForAddress(address)
    }
}