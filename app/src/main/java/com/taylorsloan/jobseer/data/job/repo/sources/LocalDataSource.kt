package com.taylorsloan.jobseer.data.job.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.local.service.JobDao
import com.taylorsloan.jobseer.data.job.util.JobMapper
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class LocalDataSource(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var jobDao: JobDao

    init {
        dataModule.storageComponent().inject(this)
    }

    private fun formatQuery(query: String?) : String {
        return if (query == null) {
            "%%"
        } else {
            "%$query%"
        }
    }

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int,
                      saved: Boolean?): Flowable<DataResult<List<Job>>> {
        val params = Triple(formatQuery(description),
                formatQuery(location),
                fullTime)
        return Flowable.just(params)
                .flatMap {
                    jobDao.loadJobs(description = params.first,
                            location = params.second)
                }
                .subscribeOn(Schedulers.io())
                .map {
                    val convertedJobs = JobMapper.mapLocalListToDomain(it)
                    DataResult(data = convertedJobs)
                }
    }

    override fun job(id: String): Flowable<DataResult<Job>> {
        return jobDao.loadJob(id)
                .subscribeOn(Schedulers.io())
                .map { DataResult(data = JobMapper.mapToDomain(it)) }
    }

    override fun savedJobs(): Flowable<DataResult<List<Job>>> {
        return jobDao.loadJobs(saved = 1)
                .subscribeOn(Schedulers.io())
                .map {
                    val convertedList = JobMapper.mapLocalListToDomain(it)
                    DataResult(data = convertedList)
                }
    }

    override fun clearJobs() {
        jobDao.clearJobs()
    }
}