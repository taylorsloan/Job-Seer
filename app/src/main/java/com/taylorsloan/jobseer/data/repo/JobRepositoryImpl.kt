package com.taylorsloan.jobseer.data.repo

import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.sources.jobs.DataSourceFactory
import com.taylorsloan.jobseer.data.repo.sources.jobs.JobPersistor
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by taylo on 10/29/2017.
 */
class JobRepositoryImpl : JobRepository {

    private val jobPersistor = JobPersistor(DataModuleImpl)
    private val dataSourceFactory  = DataSourceFactory(DataModuleImpl, jobPersistor)

    init {
        jobPersistor.init()
    }

    override fun getJobs(description: String?,
                         location: String?,
                         lat: Double?,
                         long: Double?,
                         fullTime: Boolean?,
                         saved: Boolean?): Observable<DataResult<List<Job>>> =
            dataSourceFactory.jobs(description = description, saved = saved)

    override fun getMoreJobs(page: Int){
        dataSourceFactory.getMoreJobs(page)
    }

    override fun getJob(id: String): Observable<DataResult<Job>> = dataSourceFactory.job(id)

    override fun saveJob(id: String) {
        dataSourceFactory.job(id)
                .singleElement()
                .doOnSuccess {
                    it.error?.let {
                        throw it
                    }
                }
                .subscribe(
                        {
                            it.data?.let {
                                it.saved= true
                                jobPersistor.persist(arrayListOf(it))
                            }
                        },
                        {
                            Timber.e("Could not save job", it)
                        }
                )
    }

    override fun clearJobs() {
        dataSourceFactory.clearJobs()
    }
}