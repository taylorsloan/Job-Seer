package com.taylorsloan.jobseer.dagger.module

import android.app.Application
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.model.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import javax.inject.Singleton

/**
 * Created by taylorsloan on 10/28/17.
 */
@Module
class StorageModule(val application: Application) {

    private val boxStore = MyObjectBox.builder().androidContext(application).build()

    @Provides
    @Singleton
    fun provideJobStore() : Box<Job>{
        return boxStore.boxFor(Job::class.java)
    }
}