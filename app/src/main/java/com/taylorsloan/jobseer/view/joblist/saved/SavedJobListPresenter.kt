package com.taylorsloan.jobseer.view.joblist.browse

import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.domain.job.GetJobs
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by taylo on 10/29/2017.
 */
class SavedJobListPresenter(var view: JobListContract.View?) : JobListContract.Presenter {

    private val disposable = CompositeDisposable()

    private val getJobs = GetJobs()

    init {
        getJobs.saved = true
    }

    override fun subscribe() {
        loadData()
    }

    private fun loadData(){
        getJobs.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext{
                    it.error?.let {

                    }
                }
                .subscribe(
                        {
                            view?.hideLoading()
                            view?.hideRefreshing()
                            view?.showJobs(it.data!!)
                        },
                        {
                            Timber.e(it, "Error Getting Jobs")
                        },
                        {},
                        {
                            disposable.add(it)
                        }
                )
    }

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun loadMore(page: Int) {
    }

    override fun searchJobs(query: String) {
        disposable.clear()
        getJobs.description = query
        loadData()
    }

    override fun refresh() {
    }

    override fun openJobDetail(job: LocalJob) {
        view?.showJobDetail(job)
    }
}