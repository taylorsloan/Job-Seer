package com.taylorsloan.jobseer.domain.job

import com.taylorsloan.jobseer.data.common.model.DataResult
import com.taylorsloan.jobseer.data.job.repo.JobRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import com.taylorsloan.jobseer.domain.job.models.Job
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by taylo on 11/27/2017.
 */
class GetSavedJobs(var description: String? = null,
                   var location: String? = null,
                   var fullTime: Boolean? = null) : BaseUseCase<Flowable<DataResult<List<Job>>>> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute(): Flowable<DataResult<List<Job>>> {
        return jobRepo.getSavedJobs(description, location, fullTime)
    }
}