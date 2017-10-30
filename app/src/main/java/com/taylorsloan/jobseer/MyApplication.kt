package com.taylorsloan.jobseer

import android.app.Application
import com.taylorsloan.jobseer.dagger.component.AppComponent
import com.taylorsloan.jobseer.dagger.component.DaggerAppComponent
import com.taylorsloan.jobseer.dagger.module.view.AppModule
import timber.log.Timber

/**
 * Created by taylorsloan on 10/28/17.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun initLogging(){
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

    }

    companion object {
        lateinit var appComponent : AppComponent
            private set
    }
}