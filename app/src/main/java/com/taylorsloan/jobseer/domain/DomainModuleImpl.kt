package com.taylorsloan.jobseer.domain

import com.taylorsloan.jobseer.dagger.component.DataComponent
import com.taylorsloan.jobseer.data.DataModuleImpl

/**
 * Created by taylo on 10/29/2017.
 */
object DomainModuleImpl : DomainModule {
    override fun dataComponent(): DataComponent {
        return DataModuleImpl.dataComponent()
    }
}