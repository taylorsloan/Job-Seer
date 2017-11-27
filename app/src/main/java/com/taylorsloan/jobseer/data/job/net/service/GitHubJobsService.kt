package com.taylorsloan.jobseer.data.job.net.service

import com.taylorsloan.jobseer.data.job.net.model.NetJob
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface that defines how to get jobs from Github's Jobs API
 * Created by taylorsloan on 10/28/17.
 */
interface GitHubJobsService {

    /**
     * Gets jobs from Github's Jobs API
     * @param description Searches for jobs based on description
     * @param location Gets jobs based on location
     * @param lat Gets jobs using a latitude
     * @param long Gets jobs using a longitude
     * @param fullTime Specifies whether looking for fulltime or not
     * @param page Gets a specific page from the results (limit is 50 results per page)
     */
    @GET("/positions.json")
    fun getJobs(@Query("description") description: String? = null,
                @Query("location") location: String? = null,
                @Query("lat") lat: Double? = null,
                @Query("long") long: Double? = null,
                @Query("full_time") fullTime: Boolean? = null,
                @Query("page") page: Int = 0): Single<List<NetJob>>

    /**
     * Gets a single job from Github's Jobs API
     * @param id The id of the specific job to be returned
     */
    @GET("/positions/{id}.json")
    fun getJob(@Path("id") id: String): Single<NetJob>
}