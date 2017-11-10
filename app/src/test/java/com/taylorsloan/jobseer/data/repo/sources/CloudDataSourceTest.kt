package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.AbstractObjectBoxTest
import com.taylorsloan.jobseer.data.TestDataModuleImpl
import org.junit.Before
import org.junit.Test

/**
 * Created by taylo on 10/29/2017.
 */
class CloudDataSourceTest : AbstractObjectBoxTest(){

    private lateinit var dataSource : CloudDataSource

    @Before
    override fun setUp() {
        super.setUp()
        dataSource = CloudDataSource(TestDataModuleImpl)
    }

    @Test
    fun testJobs(){
        dataSource.jobs().test().assertNoErrors()
    }

    @Test
    fun testJob(){
        dataSource.jobs().flatMap {
            dataSource.job(it.data!![0].id!!)
        }.test().assertNoErrors()
    }
}