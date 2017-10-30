package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.JobModule
import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.domain.jobs.GetJob
import com.taylorsloan.jobseer.domain.jobs.GetJobs
import dagger.Component

/**
 * Created by taylo on 10/29/2017.
 */
@DataScope
@Component(modules = arrayOf(JobModule::class))
interface DataComponent {
    fun inject(getJob: GetJob)
    fun inject(getJobs: GetJobs)
}