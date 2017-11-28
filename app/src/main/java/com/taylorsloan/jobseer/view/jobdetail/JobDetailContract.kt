package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.domain.job.models.Job
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylorsloan on 11/9/17.
 */
interface JobDetailContract {

    interface View{
        fun showJob(job: Job)
        fun showLocation(location : Pair<Double, Double>)
        fun showRemoteLocation()
        fun showCompanyWebPage(url : String)
        fun showCompanyWebPageLoadError()
        fun showSaved()
        fun showUnsaved()
        fun showShareJobDialog(company: String, position: String, link: String)
    }

    interface Presenter : BasePresenter{
        fun openCompanyWebPage()
        fun openApplicationPage()
        fun openShareJobDialog()
        fun saveJob()
        fun unsaveJob()
    }
}