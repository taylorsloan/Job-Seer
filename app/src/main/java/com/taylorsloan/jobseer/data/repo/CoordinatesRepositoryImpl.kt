package com.taylorsloan.jobseer.data.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.repo.sources.coordinates.DataSourceFacory
import io.reactivex.Single

/**
 * Created by taylorsloan on 11/11/17.
 */
class CoordinatesRepositoryImpl : CoordinatesRepository{

    private val dataSourceFactory = DataSourceFacory(DataModuleImpl)

    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return dataSourceFactory.getCoordinatesForAddress(address)
    }
}