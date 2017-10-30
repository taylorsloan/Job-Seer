package com.taylorsloan.jobseer.data

import com.taylorsloan.jobseer.dagger.component.DataComponent
import com.taylorsloan.jobseer.dagger.component.NetComponent
import com.taylorsloan.jobseer.dagger.component.StorageComponent

/**
 * Created by taylo on 10/29/2017.
 */
interface DataModule {

    fun netComponent() : NetComponent
    fun storageComponent() : StorageComponent
    fun dataComponent() : DataComponent
}