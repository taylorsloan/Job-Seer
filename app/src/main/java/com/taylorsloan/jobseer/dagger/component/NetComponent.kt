package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.repo.sources.CloudDataSource
import dagger.Component

/**
 * Created by taylorsloan on 10/28/17.
 */
@Component(modules = arrayOf(NetModule::class))
@DataScope
interface NetComponent {
    fun inject(cloudDataSource: CloudDataSource)
}