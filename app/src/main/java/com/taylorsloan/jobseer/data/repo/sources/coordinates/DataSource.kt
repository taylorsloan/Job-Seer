package com.taylorsloan.jobseer.data.repo.sources.coordinates

import io.reactivex.Single

/**
 * Created by taylorsloan on 11/11/17.
 */
interface DataSource {

    fun getCoordinatesForAddress(address: String) : Single<Pair<Double, Double>>
}