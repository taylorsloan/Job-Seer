package com.taylorsloan.jobseer.dagger.component

import android.app.Application
import com.taylorsloan.jobseer.dagger.module.view.AppModule
import com.taylorsloan.jobseer.dagger.module.JobModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by taylo on 10/29/2017.
 */
@Component(modules = arrayOf(AppModule::class, JobModule::class))
@Singleton
interface AppComponent {
    fun application() : Application
}