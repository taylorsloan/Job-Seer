package com.taylorsloan.jobseer.data.service

import com.taylorsloan.jobseer.data.TestDataModuleImpl
import org.junit.Test
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class GitHubJobsServiceTest {

    @Inject
    lateinit var githubService : GitHubJobsService

    init {
        TestDataModuleImpl.netComponent().inject(this)
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