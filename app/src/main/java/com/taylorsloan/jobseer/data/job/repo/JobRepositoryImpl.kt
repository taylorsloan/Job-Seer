package com.taylorsloan.jobseer.data.job.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.local.service.JobDao
import com.taylorsloan.jobseer.data.job.repo.sources.DataSourceFactory
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Implementation of a job repository
 * Created by taylo on 10/29/2017.
 */
class JobRepositoryImpl : JobRepository {

    @Inject
    lateinit var jobDao : JobDao

    init {
        DataModuleImpl.storageComponent().inject(this)
    }

    private val dataSourceFactory  = DataSourceFactory(DataModuleImpl)

    override fun getJobs(description: String?,
                         location: String?,
                         lat: Double?,
                         long: Double?,
                         fullTime: Boolean?,
                         saved: Boolean?): Flowable<DataResult<List<Job>>> =
            dataSourceFactory.jobs(description = description, saved = saved)

    override fun getMoreJobs(page: Int){
        dataSourceFactory.getMoreJobs(page)
    }

    override fun getJob(id: String): Flowable<DataResult<Job>> = dataSourceFactory.job(id)

    override fun saveJob(id: String) {
        jobDao.saveJob(1, id)
    }

    override fun getSavedJobs(): Flowable<DataResult<List<Job>>> {
        return dataSourceFactory.savedJobs()
    }

    override fun unsaveJob(id: String) {
        jobDao.saveJob(0, id)
    }

    override fun clearJobs() {
        dataSourceFactory.clearJobs()
    }
}