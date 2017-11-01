package com.taylorsloan.jobseer.domain.jobs

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.JobRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylo on 10/29/2017.
 */
class RefreshJobs : BaseUseCase<Unit> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute() {
        jobRepo.clearJobs()
        jobRepo.getMoreJobs(0)
    }
}