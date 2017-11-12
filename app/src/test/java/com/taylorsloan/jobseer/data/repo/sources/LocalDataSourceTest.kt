package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.AbstractObjectBoxTest
import com.taylorsloan.jobseer.data.TestDataModuleImpl
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.repo.sources.jobs.LocalDataSource
import org.junit.Before
import org.junit.Test

/**
 * Created by taylo on 10/29/2017.
 */
class LocalDataSourceTest : AbstractObjectBoxTest(){

    private lateinit var dataSource : LocalDataSource

    @Before
    override fun setUp() {
        super.setUp()
        dataSource = LocalDataSource(TestDataModuleImpl)
    }

    @Test
    fun testJobs(){
        dataSource.jobs().test().assertNoErrors().dispose()
    }

    @Test
    fun testJob(){
        dataSource.jobs().single(DataResult(data = ArrayList<Job>(0)))
                .map { dataSource.job(it.data!![0].id!!) }
                .test().assertNoErrors().dispose()
    }

}