package com.taylorsloan.jobseer.domain.job

import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.data.job.repo.JobRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylo on 10/29/2017.
 */
class GetJob(private val jobId: String) : BaseUseCase<Observable<DataResult<LocalJob>>> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute(): Observable<DataResult<LocalJob>> {
        return jobRepo.getJob(jobId)
    }
}