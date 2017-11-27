package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.dagger.scope.DataScope
import dagger.Component

/**
 * Created by taylorsloan on 10/28/17.
 */
@Component(modules = arrayOf(NetModule::class))
@DataScope
interface NetComponent {
    fun inject(cloudDataSource: com.taylorsloan.jobseer.data.job.repo.sources.CloudDataSource)
    fun inject(cloudDataSource: com.taylorsloan.jobseer.data.coordinates.repo.sources.CloudDataSource)
}