package com.taylorsloan.jobseer.view.joblist

import com.taylorsloan.jobseer.domain.jobs.GetJobs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by taylo on 10/29/2017.
 */
class JobListPresenter(var view: JobListContract.View?) : JobListContract.Presenter {

    private val disposable = CompositeDisposable()

    val getJobs = GetJobs()

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
        disposable.dispose()
    }

    override fun loadMore(page: Int) {
        var disposable : Disposable? = null
        getJobs.page = page
        getJobs.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view?.showJobs(it)
                        },
                        {},
                        {
                            disposable?.dispose()
                        },
                        {
                            disposable = it
                            this.disposable.add(it)
                        }
                )
    }
}