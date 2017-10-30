package com.taylorsloan.jobseer.view.joblist

import com.taylorsloan.jobseer.domain.jobs.GetJobs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
                .subscribe(
                        {
                            view?.showJobs(it)
                        },
                        {},
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
        getJobs.page = page
        getJobs.getMore()
    }
}