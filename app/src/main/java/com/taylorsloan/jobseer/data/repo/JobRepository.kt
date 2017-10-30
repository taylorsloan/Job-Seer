package com.taylorsloan.jobseer.data.repo

import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Observable

/**
 * Created by taylo on 10/29/2017.
 */
interface JobRepository {
    fun jobs(description: String? = null,
             location: String? = null,
             lat: Double? = null,
             long: Double? = null,
             fullTime: Boolean? = null,
             page: Int = 0) : Observable<List<Job>>

    fun job(id: String) : Observable<Job>
}