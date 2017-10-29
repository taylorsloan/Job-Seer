package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.data.service.GitHubJobsServiceTest
import dagger.Component
import javax.inject.Singleton

/**
 * Created by taylorsloan on 10/28/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class))
interface TestNetComponent {
    fun inject(serviceTest: GitHubJobsServiceTest)
}