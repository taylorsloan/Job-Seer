package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylorsloan on 11/9/17.
 */
interface JobDetailContract {

    interface View{
        fun showJob(job: LocalJob)
        fun showLocation(location : Pair<Double, Double>)
        fun showRemoteLocation()
        fun showCompanyWebPage(url : String)
        fun showCompanyWebPageLoadError()
        fun showShareJobDialog(company: String, position: String, link: String)
    }

    interface Presenter : BasePresenter{
        fun openCompanyWebPage()
        fun openApplicationPage()
        fun openShareJobDialog()
    }
}