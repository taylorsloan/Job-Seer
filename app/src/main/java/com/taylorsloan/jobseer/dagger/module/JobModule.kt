package com.taylorsloan.jobseer.dagger.module

import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.repo.JobRepository
import com.taylorsloan.jobseer.data.repo.JobRepositoryImpl
import dagger.Module
import dagger.Provides

/**
 * Created by taylo on 10/29/2017.
 */
@Module
class JobModule {

    @Provides
    @DataScope
    fun provideJobRepo() : JobRepository{
        return JobRepositoryImpl()
    }
}