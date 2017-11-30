package com.taylorsloan.jobseer.data.job.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.DataModuleImpl
import com.taylorsloan.jobseer.data.common.AppDatabase
import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.util.JobMapper
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source wrapper for jobs being returned from local or online sources
 * Created by taylorsloan on 10/28/17.
 */
class DataSourceFactory(dataModule: DataModule) : DataSource {

    @Inject
    lateinit var appDb : AppDatabase

    private val localDataStore : DataSource = LocalDataSource(dataModule)
    private val cloudDataStore : DataSource = CloudDataSource(dataModule)

    private var localJobsDisposable : Disposable? = null

    private val subject: BehaviorSubject<DataResult<List<Job>>> = BehaviorSubject.create()

    private var previousSearchParams : SearchParams? = null

    private data class SearchParams(val description: String? = null,
                                    val location: String? = null,
                                    val lat: Double? = null,
                                    val long: Double? = null,
                                    val fullTime: Boolean? = null,
                                    val saved: Boolean? = null)

    init {
        DataModuleImpl.storageComponent().inject(this)
    }

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int,
                      saved: Boolean?): Flowable<DataResult<List<Job>>> {
        val searchParams = SearchParams(description, location, lat, long, fullTime, saved)
        if (previousSearchParams?.hashCode() != searchParams.hashCode()){
            localJobsDisposable?.dispose()
            localJobsDisposable = localDataStore.jobs(description, location, lat, long, fullTime, page, saved)
                    .subscribe(
                            { subject.onNext(it) },
                            { subject.onError(it) },
                            { subject.onComplete() }
                    )
        }
        previousSearchParams = searchParams
        return subject.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getMoreJobs(page: Int) {
        previousSearchParams?.let {
            cloudDataStore.jobs(it.description, it.location, it.lat, it.long, it.fullTime, page)
                    .doOnNext{
                        it.data?.let {
                            appDb.jobDao().insertJobs(JobMapper.mapDomainListToLocal(it))
                        }
                    }
                    .subscribe(
                            {
                                Timber.d("Received Jobs: %s", it.data?.size.toString())                      },
                            {
                                Timber.e(it)
                                subject.onNext(DataResult(error = it))
                            }
                    )
        }
    }

    override fun job(id: String): Flowable<DataResult<Job>> {
        cloudDataStore.job(id)
                .subscribe(
                        {
                            it.data?.let {
                                appDb.jobDao().insertJob(JobMapper.mapToLocal(it))
                            }
                        },
                        {
                            Timber.e(it)
                            subject.onNext(DataResult(error = it))
                        }
                )
        return localDataStore.job(id)
    }

    override fun savedJobs(): Flowable<DataResult<List<Job>>> {
        return localDataStore.savedJobs()
    }

    override fun clearJobs() {
        localDataStore.clearJobs()
    }
}