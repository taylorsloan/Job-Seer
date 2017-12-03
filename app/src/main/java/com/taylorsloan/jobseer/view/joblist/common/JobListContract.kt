package com.taylorsloan.jobseer.view.joblist.common

import com.taylorsloan.jobseer.domain.job.models.Job
import com.taylorsloan.jobseer.view.BasePresenter

/**
 * Created by taylo on 10/29/2017.
 */
interface JobListContract {

    interface View {
        fun showJobs(jobs: List<Job>)
        fun showLoading()
        fun hideLoading()
        fun hideRefreshing()
        fun showJobDetail(job: Job)
        fun searchJobs(query: String, location: String, fullTime: Boolean)
    }

    interface Presenter : BasePresenter{
        fun loadMore(page: Int)
        fun refresh()
        fun openJobDetail(job: Job)
        fun searchJobs(query: String, location: String, fullTime: Boolean)
    }
}