package com.taylorsloan.jobseer.domain.job

import com.taylorsloan.jobseer.data.job.repo.JobRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import javax.inject.Inject

/**
 * Created by taylo on 10/29/2017.
 */
class UnsaveJob(val id : String) : BaseUseCase<Unit> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute() {
        jobRepo.unsaveJob(id)
    }
}