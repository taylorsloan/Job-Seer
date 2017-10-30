package com.taylorsloan.jobseer.dagger.component

import com.taylorsloan.jobseer.AbstractObjectBoxTest
import com.taylorsloan.jobseer.dagger.module.TestStorageModule
import com.taylorsloan.jobseer.dagger.scope.DataScope
import dagger.Component

/**
 * Created by taylorsloan on 10/28/17.
 */
@Component(modules = arrayOf(TestStorageModule::class))
@DataScope
interface TestStorageComponent : StorageComponent{
    fun inject(objectBoxTest: AbstractObjectBoxTest)
}