package com.taylorsloan.jobseer.data.job.repo

import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable

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
                saved: Boolean? = null): Flowable<DataResult<List<Job>>>

    fun getSavedJobs(description: String? = null,
                     location: String? = null,
                     fullTime: Boolean? = null) : Flowable<DataResult<List<Job>>>

    fun getMoreJobs(page: Int = 0)

    fun getJob(id: String): Flowable<DataResult<Job>>

    fun saveJob(id: String)

    fun unsaveJob(id: String)

    fun clearJobs()
}