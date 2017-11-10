package com.taylorsloan.jobseer.view.jobdetail

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylorsloan on 11/9/17.
 */
interface JobDetailContract {

    interface View{
        fun showJob(job: Job)
    }

    interface Presenter : BasePresenter{
    }
}