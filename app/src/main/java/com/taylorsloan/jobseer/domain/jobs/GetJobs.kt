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
class GetJobs(var page: Int = 0) : BaseUseCase<Observable<List<Job>>> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute(): Observable<List<Job>> {
        return jobRepo.jobs(page = page)
    }
}