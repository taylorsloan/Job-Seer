package com.taylorsloan.jobseer.dagger.module

import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.coordinate.repo.CoordinatesRepository
import com.taylorsloan.jobseer.data.coordinate.repo.CoordinatesRepositoryImpl
import com.taylorsloan.jobseer.data.job.repo.JobRepository
import com.taylorsloan.jobseer.data.job.repo.JobRepositoryImpl
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

    @Provides
    @DataScope
    fun provideCoordinatesRepo(): CoordinatesRepository {
        return CoordinatesRepositoryImpl()
    }
}