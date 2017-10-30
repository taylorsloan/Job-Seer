package com.taylorsloan.jobseer.dagger.module.view

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by taylorsloan on 10/28/17.
 */
@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() : Application{
        return application
    }

}