package com.taylorsloan.jobseer.view.joblist.browse

import com.taylorsloan.jobseer.domain.job.GetJobs
import com.taylorsloan.jobseer.domain.job.RefreshJobs
import com.taylorsloan.jobseer.domain.job.models.Job
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by taylo on 10/29/2017.
 */
class BrowseJobListPresenter(var view: JobListContract.View?) : JobListContract.Presenter {

    private val disposable = CompositeDisposable()

    private val getJobs = GetJobs()

    override fun subscribe() {
        loadData()
    }

    private fun loadData(){
        disposable.add(getJobs.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext{
                    it.error?.let {

                    }
                    if (!it.loading) {
                        view?.hideLoading()
                        view?.hideRefreshing()
                    }
                }
                .subscribe(
                        {
                            view?.showJobs(it.data!!)
                        },
                        {
                            Timber.e(it, "Error Getting Jobs")
                        },
                        {})
                )
        getJobs.getMore()
    }

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun loadMore(page: Int) {
        getJobs.page = page
        getJobs.getMore()
        view?.showLoading()
    }

    override fun searchJobs(query: String, location: String, fullTime: Boolean) {
        Timber.d("Query: %s, Location: %s, Fulltime: %s", query, location, fullTime)
        disposable.clear()
        getJobs.apply {
            page = 0
            description = query
            this.location = location
            this.fullTime = fullTime
        }
        loadData()
    }

    override fun refresh() {
        RefreshJobs().execute()
    }

    override fun openJobDetail(job: Job) {
        view?.showJobDetail(job)
    }
}