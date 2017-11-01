package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

/**
 * Created by taylorsloan on 10/28/17.
 */
class DataSourceFactory(private val dataModule: DataModule) : DataSource{

    private val localDataStore : DataSource = LocalDataSource(dataModule)
    private val cloudDataStore : DataSource = CloudDataSource(dataModule)

    private var localJobsDisposable : Disposable? = null
    private var localJobDisposable : Disposable? = null

    private var jobDisposable : Disposable? = null

    private val subject : BehaviorSubject<List<Job>> = BehaviorSubject.create()

    private var previousSearchParams : SearchParams? = null

    private data class SearchParams(val description: String? = null,
                                    val location: String? = null,
                                    val lat: Double? = null,
                                    val long: Double? = null,
                                    val fullTime: Boolean? = null)

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int): Observable<List<Job>> {
        val searchParams = SearchParams(description, location, lat, long, fullTime)
        if (previousSearchParams?.hashCode() != searchParams.hashCode()){
            localJobsDisposable?.dispose()
            localJobsDisposable = localDataStore.jobs(description, location, lat, long, fullTime)
                    .subscribeWith(object: DisposableObserver<List<Job>>(){
                        override fun onError(e: Throwable) {
                            subject.onError(e)
                        }

                        override fun onNext(t: List<Job>) {
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
                    .compose(JobPersistor(dataModule))
                    .subscribe(
                            {
                                Timber.d("Received Jobs: %s", it.size.toString())
                            },
                            {
                                Timber.e(it)
                                subject.onError(it)
                            }
                    )
        }
    }

    override fun job(id: String): Observable<Job> {
        cloudDataStore.job(id)
                .subscribe(
                        {},
                        {},
                        {
                            jobDisposable?.dispose()
                        },
                        {
                            jobDisposable = it
                        }
                )
        return localDataStore.job(id)
    }

    override fun clearJobs() {
        localDataStore.clearJobs()
    }
}