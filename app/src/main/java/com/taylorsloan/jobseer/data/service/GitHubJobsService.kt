package com.taylorsloan.jobseer.data.service

import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by taylorsloan on 10/28/17.
 */
interface GitHubJobsService {

    @GET("/positions.json")
    fun getJobs(@Query("description") description: String? = null,
                @Query("location") location: String? = null,
                @Query("lat") lat: Double? = null,
                @Query("long") long: Double? = null,
                @Query("full_time") fullTime: Boolean? = null,
                @Query("page") page: Int = 0) : Single<List<Job>>

    @GET("/positions/{id}.json")
    fun getJob(@Path("id") id: String) : Single<Job>
}