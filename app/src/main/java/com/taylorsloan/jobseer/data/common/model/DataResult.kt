package com.taylorsloan.jobseer.data.common.model

/**
 * Wrapper class that can contain either data returned from local DB queries or errors from DB or
 * online
 * Created by taylo on 11/1/2017.
 */
data class DataResult<out T>(val data: T? = null,
                             val error: Throwable? = null)