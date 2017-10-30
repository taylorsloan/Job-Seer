package com.taylorsloan.jobseer.data

import com.taylorsloan.jobseer.MyApplication
import com.taylorsloan.jobseer.dagger.component.*
import com.taylorsloan.jobseer.dagger.module.JobModule
import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.dagger.module.StorageModule

/**
 * Created by taylorsloan on 10/28/17.
 */
object DataModuleImpl : DataModule{

    const val GITHUB_JOBS_URL  = "https://jobs.github.com"

    private val netComponent : NetComponent = DaggerNetComponent.builder()
            .netModule(NetModule(GITHUB_JOBS_URL))
            .build()

    private val storageComponent : StorageComponent = DaggerStorageComponent.builder()
            .appComponent(MyApplication.appComponent)
            .storageModule(StorageModule())
            .build()

    private val dataComponent : DataComponent = DaggerDataComponent.builder()
            .jobModule(JobModule())
            .build()

    override fun netComponent(): NetComponent {
        return netComponent
    }

    override fun storageComponent(): StorageComponent {
        return storageComponent
    }

    override fun dataComponent(): DataComponent {
        return dataComponent
    }
}