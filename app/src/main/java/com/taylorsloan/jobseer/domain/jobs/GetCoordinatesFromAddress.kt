package com.taylorsloan.jobseer.domain.jobs

import com.taylorsloan.jobseer.data.repo.CoordinatesRepository
import com.taylorsloan.jobseer.domain.BaseUseCase
import com.taylorsloan.jobseer.domain.DomainModuleImpl
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by taylorsloan on 11/11/17.
 */
class GetCoordinatesFromAddress(private val address: String) : BaseUseCase<Single<Pair<Double, Double>>> {

    @Inject
    lateinit var coordinateRepo : CoordinatesRepository

    init {
        DomainModuleImpl.dataComponent().inject(this)
    }

    override fun execute(): Single<Pair<Double, Double>> {
        return coordinateRepo.getCoordinatesForAddress(address)
    }
}