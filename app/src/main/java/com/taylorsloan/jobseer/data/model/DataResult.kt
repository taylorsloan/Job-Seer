package com.taylorsloan.jobseer.data.model

/**
 * Created by taylo on 11/1/2017.
 */
data class DataResult<out T>(val data: T? = null, val error: Throwable? = null)