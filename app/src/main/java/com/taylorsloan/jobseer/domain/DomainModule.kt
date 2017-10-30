package com.taylorsloan.jobseer.domain

import com.taylorsloan.jobseer.dagger.component.DataComponent

/**
 * Created by taylo on 10/29/2017.
 */
interface DomainModule {
    fun dataComponent() : DataComponent
}