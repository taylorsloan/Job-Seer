package com.taylorsloan.jobseer.data.coordinate.repo

import io.reactivex.Single

/**
 * Interface defining a coordinate repository
 * Created by taylorsloan on 11/11/17.
 */
interface CoordinatesRepository {

    /**
     * Gets coordinates using an address
     * @param address The address to get coordinates for
     */
    fun getCoordinatesForAddress(address: String) : Single<Pair<Double, Double>>
}