package com.taylorsloan.jobseer.domain.jobs

import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.JobRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylo on 10/29/2017.
 */
class GetJobs(var page: Int = 0,
              var title: String? = null,
              var description: String? = null,
              var location: String? = null,
              var lat: Double? = null,
              var long: Double? = null,
              var fullTime: Boolean? = null,
              var saved: Boolean? = null) : BaseUseCase<Observable<DataResult<List<Job>>>> {

    @Inject
    lateinit var jobRepo : JobRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute(): Observable<DataResult<List<Job>>> {
        return jobRepo.getJobs(description, location, lat, long, fullTime, saved)
    }

    fun getMore(){
        jobRepo.getMoreJobs(page = page)
    }
}