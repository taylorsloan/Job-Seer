package com.taylorsloan.jobseer.data.coordinate.repo.sources

import io.reactivex.Single

/**
 * Describes a latlong data source from an address
 * Created by taylorsloan on 11/11/17.
 */
interface DataSource {

    /**
     * Gets coordinates using an address
     * @param address The address to get coordinates for
     */
    fun getCoordinatesForAddress(address: String) : Single<Pair<Double, Double>>
}