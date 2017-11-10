package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.domain.jobs.GetJob
import io.reactivex.disposables.Disposable

/**
 * Created by taylorsloan on 11/9/17.
 */
class JobDetailPresenter(private var view: JobDetailContract.View?, private val jobId: String) :
        JobDetailContract.Presenter {

    private var jobDisposable : Disposable? = null

    override fun subscribe() {
        jobDisposable = GetJob(jobId).execute().subscribe(
                {
                    it.data?.let {
                        view?.showJob(it)
                    }
                },
                {}
        )
    }

    override fun unsubscribe() {
    }
}