package com.taylorsloan.jobseer.view.joblist.common

import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylo on 10/29/2017.
 */
interface JobListContract {

    interface View {
        fun showJobs(jobs: List<LocalJob>)
        fun showLoading()
        fun hideLoading()
        fun hideRefreshing()
        fun showJobDetail(job: LocalJob)
        fun searchJobs(query: String)
    }

    interface Presenter : BasePresenter{
        fun loadMore(page: Int)
        fun refresh()
        fun openJobDetail(job: LocalJob)
        fun searchJobs(query: String)
    }
}