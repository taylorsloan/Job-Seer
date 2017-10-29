package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.StorageModule
import com.taylorsloan.jobseer.data.repo.sources.LocalDataSource
import dagger.Component
import javax.inject.Singleton

/**
 * Created by taylorsloan on 10/28/17.
 */
@Singleton
@Component(modules = arrayOf(StorageModule::class))
interface StorageComponent {
    fun inject(localDataSource: LocalDataSource)
}