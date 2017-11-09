package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.service.GitHubJobsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class CloudDataSource(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var githubService : GitHubJobsService

    init {
        dataModule.netComponent().inject(this)
    }

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int): Observable<DataResult<List<Job>>> {
        return githubService.getJobs(description, location, lat, long, fullTime, page)
                .map { DataResult(data = it) }
                .toObservable()
    }

    override fun job(id: String): Observable<DataResult<Job>> {
        return githubService.getJob(id)
                .map { DataResult(data = it) }
                .toObservable()
    }

    override fun clearJobs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}