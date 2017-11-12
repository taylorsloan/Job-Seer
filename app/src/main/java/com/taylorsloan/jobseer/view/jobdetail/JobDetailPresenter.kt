package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.domain.jobs.GetCoordinatesFromAddress
import com.taylorsloan.jobseer.domain.jobs.GetJob
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by taylorsloan on 11/9/17.
 */
class JobDetailPresenter(private var view: JobDetailContract.View?, private val jobId: String) :
        JobDetailContract.Presenter {

    private var jobDisposable : Disposable? = null
    private var job : Job? = null

    override fun subscribe() {
        jobDisposable = GetJob(jobId).execute()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    it.data?.location?.let {
                        if (!isRemoteJob(it)) {
                            getCoordinatesForAddress(it)
                        }else{
                            view?.showRemoteLocation()
                        }
                    }
                }
                .subscribe(
                {
                    it.data?.let {
                        job = it
                        view?.showJob(it)
                    }
                },
                {}
        )

    }

    override fun unsubscribe() {
        jobDisposable?.dispose()
    }

    private fun isRemoteJob(location: String) : Boolean{
        return location.contains("remote", true)
    }

    override fun openCompanyWebPage() {
        val link = job?.company_url
        if (!link.isNullOrEmpty()) {
            view?.showCompanyWebPage(link!!)
        } else {
            view?.showCompanyWebPageLoadError()
        }
    }

    private fun getCoordinatesForAddress(address : String){
        GetCoordinatesFromAddress(address).execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view?.showLocation(it)
                        },
                        {
                            Timber.e(it, "Could not Retrieve Coordinates")
                        })
    }
}