package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.Job
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by taylorsloan on 10/28/17.
 */
class DataSourceFactory(private val dataModule: DataModule) : DataSource{

    private val localDataStore : DataSource = LocalDataSource(dataModule)
    private val cloudDataStore : DataSource = CloudDataSource(dataModule)

    private var jobsDisposable : Disposable? = null
    private var jobDisposable : Disposable? = null

    override fun jobs(description: String?,
                      location: String?,
                      lat: Double?,
                      long: Double?,
                      fullTime: Boolean?,
                      page: Int): Observable<List<Job>> {
        cloudDataStore.jobs(description, location, lat, long, fullTime, page)
                .compose(JobPersistor(dataModule))
                .subscribe(
                        {
                            Timber.d("Received Jobs: %s", it.size.toString())
                        },
                        {
                            Timber.e(it)
                        },
                        {
                            jobsDisposable?.dispose()
                        },
                        {
                            jobsDisposable = it
                        }
                )
        return localDataStore.jobs(description, location, lat, long, fullTime)
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
}