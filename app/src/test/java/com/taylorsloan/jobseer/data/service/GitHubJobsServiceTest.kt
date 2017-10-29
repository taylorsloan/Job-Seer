package com.taylorsloan.jobseer.data.service

import com.taylorsloan.jobseer.dagger.component.DaggerTestNetComponent
import com.taylorsloan.jobseer.dagger.module.NetModule
import com.taylorsloan.jobseer.data.DataModule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class GitHubJobsServiceTest {

    val netComponent = DaggerTestNetComponent.builder()
            .netModule(NetModule(DataModule.GITHUB_JOBS_URL))
            .build()

    @Inject
    lateinit var githubService : GitHubJobsService

    init {
        netComponent.inject(this)
    }

    @Test
    fun testGetJobs() {
        githubService.getJobs().test().assertNoErrors()
    }

    @Test
    fun testGetJob() {
        githubService.getJobs().doOnSuccess {
            it.get(0).let {
                githubService.getJob(it.id!!).test().assertNoErrors()
            }
        }.test().assertNoErrors()
    }
}