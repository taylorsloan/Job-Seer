package com.taylorsloan.jobseer.dagger.module

import android.app.Application
import android.arch.persistence.room.Room
import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.common.AppDatabase
import com.taylorsloan.jobseer.data.job.local.service.JobDao
import dagger.Module
import dagger.Provides


/**
 * Created by taylorsloan on 10/28/17.
 */
@Module
class StorageModule {

    @Provides
    @DataScope
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
                AppDatabase::class.java, "local.db")
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @DataScope
    fun provideJobDao(database: AppDatabase) : JobDao {
        return database.jobDao()
    }
}