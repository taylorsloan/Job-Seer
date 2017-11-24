package com.taylorsloan.jobseer.data.repo.sources.jobs

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

/**
 * Created by taylorsloan on 10/28/17.
 */
class DataSourceFactory(dataModule: DataModule, val jobPersistor: JobPersistor) : DataSource {

    private val localDataStore : DataSource = LocalDataSource(dataModule)
    private val cloudDataStore : DataSource = CloudDataSource(dataModule)

    private var localJobsDisposable : Disposable? = null
    private var localJobDisposable : Disposable? = null

    private val subject : BehaviorSubject<DataResult<List<Job>>> = BehaviorSubject.create()

    private var previousSearchParams : SearchParams? = null

    private data class SearchParams(val description: String? = null,
                                    val location: String? = null,
                                    val lat: Double? = null,
                                    val long: Double? = null,
                                    val fullTime: Boolean? = null)

    init {
        // jobPersistor.init()
    }

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int): Observable<DataResult<List<Job>>> {
        val searchParams = SearchParams(description, location, lat, long, fullTime)
        if (previousSearchParams?.hashCode() != searchParams.hashCode()){
            localJobsDisposable?.dispose()
            localJobsDisposable = localDataStore.jobs(description, location, lat, long, fullTime)
                    .subscribeWith(object: DisposableObserver<DataResult<List<Job>>>(){
                        override fun onError(e: Throwable) {
                            subject.onError(e)
                        }

                        override fun onNext(t: DataResult<List<Job>>) {
                            subject.onNext(t)
                        }

                        override fun onComplete() {
                            subject.onComplete()
                        }
                    })
        }
        previousSearchParams = searchParams
        return subject
    }

    fun getMoreJobs(page: Int) {
        previousSearchParams?.let {
            cloudDataStore.jobs(it.description, it.location, it.lat, it.long, it.fullTime, page)
                    .subscribe(
                            {
                                Timber.d("Received Jobs: %s", it.data?.size.toString())
                                it.data?.let {
                                    jobPersistor.persist(it)
                                }
                            },
                            {
                                Timber.e(it)
                                subject.onNext(DataResult(error = it))
                            }
                    )
        }
    }

    override fun job(id: String): Observable<DataResult<Job>> {
        cloudDataStore.job(id)
                .subscribe(
                        {
                            it.data?.let {
                                jobPersistor.persist(arrayListOf(it))
                            }
                        },
                        {
                            Timber.e(it)
                            subject.onNext(DataResult(error = it))
                        }
                )
        return localDataStore.job(id)
    }

    override fun clearJobs() {
        localDataStore.clearJobs()
    }
}