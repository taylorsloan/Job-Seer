package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Observable

/**
 * Created by taylorsloan on 10/28/17.
 */
interface DataSource {
    fun jobs(description: String? = null,
             location: String? = null,
             lat: Double? = null,
             long: Double? = null,
             fullTime: Boolean? = null) : Observable<List<Job>>

    fun job(id: String) : Observable<Job>
}