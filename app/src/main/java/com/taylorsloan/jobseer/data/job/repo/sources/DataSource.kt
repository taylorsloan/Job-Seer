package com.taylorsloan.jobseer.data.job.repo.sources

import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable

/**
 * Created by taylorsloan on 10/28/17.
 */
interface DataSource {
    fun jobs(description: String? = null,
             location: String? = null,
             lat: Double? = null,
             long: Double? = null,
             fullTime: Boolean? = null,
             page: Int = 0,
             saved: Boolean? = null): Flowable<DataResult<List<Job>>>

    fun job(id: String): Flowable<DataResult<Job>>

    fun clearJobs()
}