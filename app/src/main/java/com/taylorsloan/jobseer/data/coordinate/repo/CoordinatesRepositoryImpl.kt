package com.taylorsloan.jobseer.data.coordinate.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.coordinate.repo.sources.DataSourceFacory
import io.reactivex.Single

/**
 * Implementation of a coordinate repository
 * Created by taylorsloan on 11/11/17.
 */
class CoordinatesRepositoryImpl : CoordinatesRepository {

    private val dataSourceFactory = DataSourceFacory(DataModuleImpl)

    /**
     * Gets coordinates using an address
     * @param address The address to get coordinates for
     */
    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return dataSourceFactory.getCoordinatesForAddress(address)
    }
}