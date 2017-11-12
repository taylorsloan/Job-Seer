package com.taylorsloan.jobseer.data.repo

import io.reactivex.Single

/**
 * Created by taylorsloan on 11/11/17.
 */
interface CoordinatesRepository {

    fun getCoordinatesForAddress(address: String) : Single<Pair<Double, Double>>
}