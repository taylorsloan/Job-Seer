package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.data.repo.sources.CloudDataSource
import dagger.Component
import javax.inject.Singleton

/**
 * Created by taylorsloan on 10/28/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
    fun inject(cloudDataSource: CloudDataSource)
}