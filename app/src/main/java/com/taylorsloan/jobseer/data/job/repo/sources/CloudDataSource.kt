package com.taylorsloan.jobseer.data.job.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.net.service.GitHubJobsService
import com.taylorsloan.jobseer.data.job.util.JobMapper
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * An implementation of a network based job data source
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
                      page: Int,
                      saved: Boolean?): Flowable<DataResult<List<Job>>> {
        return githubService.getJobs(description, location, lat, long, fullTime, page)
                .map { JobMapper.convertNetToPlainObj(it) }
                .map { DataResult(data = it) }
                .toFlowable()
    }

    override fun job(id: String): Flowable<DataResult<Job>> {
        return githubService.getJob(id)
                .map { JobMapper.toPlainObj(it) }
                .map { DataResult(data = it) }
                .toFlowable()
    }

    override fun clearJobs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}