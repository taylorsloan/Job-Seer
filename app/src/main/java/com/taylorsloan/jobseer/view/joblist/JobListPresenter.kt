package com.taylorsloan.jobseer.view.joblist

import com.taylorsloan.jobseer.domain.jobs.GetJobs
import com.taylorsloan.jobseer.domain.jobs.RefreshJobs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by taylo on 10/29/2017.
 */
class JobListPresenter(var view: JobListContract.View?) : JobListContract.Presenter {

    private val disposable = CompositeDisposable()

    private val getJobs = GetJobs()

    override fun subscribe() {
        getJobs.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext{
                    it.error?.let {
                        throw it
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

    override fun refresh() {
        RefreshJobs().execute()
    }
}