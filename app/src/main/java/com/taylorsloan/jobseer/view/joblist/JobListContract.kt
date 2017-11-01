package com.taylorsloan.jobseer.view.joblist

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylo on 10/29/2017.
 */
interface JobListContract {

    interface View {
        fun showJobs(jobs : List<Job>)
        fun showLoading()
        fun hideLoading()
        fun hideRefreshing()
    }

    interface Presenter : BasePresenter{
        fun loadMore(page: Int)
        fun refresh()
    }
}