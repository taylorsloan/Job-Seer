package com.taylorsloan.jobseer.data.repo.sources.coordinates

import com.taylorsloan.jobseer.data.DataModule
import io.reactivex.Single

/**
 * Created by taylorsloan on 11/11/17.
 */
class DataSourceFacory(dataModule: DataModule) : DataSource {

    private val cloudDataSource = CloudDataSource(dataModule)

    override fun getCoordinatesForAddress(address: String): Single<Pair<Double, Double>> {
        return cloudDataSource.getCoordinatesForAddress(address)
    }
}