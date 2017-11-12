package com.taylorsloan.jobseer.data.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.sources.jobs.DataSourceFactory
import io.reactivex.Observable

/**
 * Created by taylo on 10/29/2017.
 */
class JobRepositoryImpl : JobRepository {

    private val dataSourceFactory  = DataSourceFactory(DataModuleImpl)

    override fun getJobs(description: String?,
                         location: String?,
                         lat: Double?,
                         long: Double?,
                         fullTime: Boolean?): Observable<DataResult<List<Job>>> {
        return dataSourceFactory.jobs()
    }

    override fun getMoreJobs(page: Int){
        dataSourceFactory.getMoreJobs(page)
    }

    override fun getJob(id: String): Observable<DataResult<Job>> {
        return dataSourceFactory.job(id)
    }

    override fun clearJobs() {
        dataSourceFactory.clearJobs()
    }
}