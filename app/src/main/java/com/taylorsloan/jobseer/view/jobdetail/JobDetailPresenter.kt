package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.domain.jobs.GetCoordinatesFromAddress
import com.taylorsloan.jobseer.domain.jobs.GetJob
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
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

    override fun openApplicationPage() {
        val applicationLink = parseApplicationLink()
        if (!applicationLink.isNullOrEmpty()){
            view?.showCompanyWebPage(applicationLink!!)
        } else {
            view?.showCompanyWebPageLoadError()
        }
    }

    private fun parseApplicationLink() : String? {
        val htmlString  = job?.how_to_apply
        val doc = Jsoup.parse(htmlString)
        val links = doc.select("a[href]")
        return links[0].attr("href")
    }

    override fun openShareJobDialog() {
        val applicationLink = parseApplicationLink()
        if (!applicationLink.isNullOrEmpty()) {
            view?.showShareJobDialog(job?.company ?: "null", job?.title ?: "null",
                    applicationLink ?: "null")
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