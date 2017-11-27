package com.taylorsloan.jobseer.data.job.repo

import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import io.reactivex.Observable

/**
 * Interface defining a job repository
 * Created by taylo on 10/29/2017.
 */
interface JobRepository {
    fun getJobs(description: String? = null,
                location: String? = null,
                lat: Double? = null,
                long: Double? = null,
                fullTime: Boolean? = null,
                saved: Boolean? = null): Observable<DataResult<List<LocalJob>>>

    fun getMoreJobs(page: Int = 0)

    fun getJob(id: String): Observable<DataResult<LocalJob>>

    fun saveJob(id: String)

    fun clearJobs()
}