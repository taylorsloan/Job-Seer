package com.taylorsloan.jobseer

import android.app.Application
import timber.log.Timber

/**
 * Created by taylorsloan on 10/28/17.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    fun initLogging(){
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}