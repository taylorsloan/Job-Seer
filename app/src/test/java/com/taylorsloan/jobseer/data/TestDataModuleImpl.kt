package com.taylorsloan.jobseer.data

import com.taylorsloan.jobseer.dagger.component.*
import com.taylorsloan.jobseer.dagger.module.JobModule
import com.taylorsloan.jobseer.dagger.module.TestNetModule
import com.taylorsloan.jobseer.dagger.module.TestStorageModule

/**
 * Created by taylorsloan on 10/28/17.
 */
object TestDataModuleImpl : DataModule{

    const val GITHUB_JOBS_URL  = "https://jobs.github.com"

    private val netComponent : TestNetComponent = DaggerTestNetComponent.builder()
            .testNetModule(TestNetModule(GITHUB_JOBS_URL))
            .build()

    private val storageComponent : TestStorageComponent = DaggerTestStorageComponent.builder()
            .testStorageModule(TestStorageModule())
            .build()

    private val dataComponent : DataComponent = DaggerDataComponent.builder()
            .jobModule(JobModule())
            .build()

    override fun netComponent(): TestNetComponent {
        return netComponent
    }

    override fun storageComponent(): TestStorageComponent {
        return storageComponent
    }

    override fun dataComponent(): DataComponent {
        return dataComponent
    }
}