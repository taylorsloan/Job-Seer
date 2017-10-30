package com.taylorsloan.jobseer.domain

/**
 * Created by taylo on 10/29/2017.
 */
interface BaseUseCase<out T> {

    fun execute() : T
}