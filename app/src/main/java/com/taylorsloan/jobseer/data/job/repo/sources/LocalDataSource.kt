package com.taylorsloan.jobseer.data.job.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.common.AppDatabase
import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.data.job.util.JobMapper
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class LocalDataSource(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var appDb: AppDatabase

    init {
        dataModule.storageComponent().inject(this)
    }

    private lateinit var jobBox: Box<LocalJob>

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int,
                      saved: Boolean?): Flowable<DataResult<List<Job>>> {
        return appDb.jobDao().loadAllJobs()
                .map {
                    val convertedJobs = JobMapper.convertLocalToPlainObj(it)
                    DataResult(data = convertedJobs)
                }
        /*val queryBuilder = jobBox.query()
        addSearchQuery(queryBuilder, description)
        addSavedQuery(queryBuilder, saved)
        return RxQuery.observable(queryBuilder.build())
                .map { JobMapper.convertLocalToPlainObj(it) }
                .map { DataResult(data = it) }*/
    }

    /*private fun addSearchQuery(queryBuilder: QueryBuilder<LocalJob>, search: String?) :
            QueryBuilder<LocalJob> {
        search?.let {
            queryBuilder.contains(LocalJob_.description, it)
        }
        return queryBuilder
    }

    private fun addSavedQuery(queryBuilder: QueryBuilder<LocalJob>, saved: Boolean?) :
            QueryBuilder<LocalJob> {
        saved?.let {
            queryBuilder.equal(LocalJob_.saved, saved)
        }
        return queryBuilder
    }*/

    override fun job(id: String): Observable<DataResult<Job>> {
        return RxQuery.observable(jobBox.query().equal(LocalJob_.id, id).build())
                .map { JobMapper.toPlainObj(it) }
                .map { DataResult(data = it[0]) }
    }

    override fun clearJobs() {
        jobBox.removeAll()
    }
}