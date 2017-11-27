package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.StorageModule
import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.job.repo.sources.LocalDataSource
import dagger.Component

/**
 * Created by taylorsloan on 10/28/17.
 */
@Component(modules = arrayOf(StorageModule::class), dependencies = arrayOf(AppComponent::class))
@DataScope
interface StorageComponent {
    fun inject(localDataSource: LocalDataSource)
}