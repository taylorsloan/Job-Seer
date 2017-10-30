package com.taylorsloan.jobseer

import com.taylorsloan.jobseer.data.TestDataModuleImpl
import org.junit.After
import org.junit.Before
import io.objectbox.BoxStore
import javax.inject.Inject


/**
 * Created by taylo on 10/29/2017.
 */
abstract class AbstractObjectBoxTest {

    @Inject
    lateinit var store: BoxStore

    @Before
    open fun setUp() {
        TestDataModuleImpl.storageComponent().inject(this)
    }

    @After
    @Throws(Exception::class)
    open fun tearDown() {
        store.close()
        store.deleteAllFiles()
    }
}