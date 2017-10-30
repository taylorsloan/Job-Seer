package com.taylorsloan.jobseer.data.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.sources.DataSourceFactory
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
                         fullTime: Boolean?): Observable<List<Job>> {
        return dataSourceFactory.jobs()
    }

    override fun getMoreJobs(description: String?, location: String?, lat: Double?, long: Double?, fullTime: Boolean?, page: Int){
        dataSourceFactory.getMoreJobs(page = page)
    }

    override fun job(id: String): Observable<Job> {
        return dataSourceFactory.job(id)
    }
}