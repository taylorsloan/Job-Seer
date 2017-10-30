package com.taylorsloan.jobseer.dagger.module

import com.taylorsloan.jobseer.dagger.scope.DataScope
import com.taylorsloan.jobseer.data.service.GitHubJobsService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by taylorsloan on 10/28/17.
 */
@Module
class TestNetModule(baseUrl : String) {

    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .build()

    @Provides
    @DataScope
    fun provideGitHubJobService() : GitHubJobsService {
        return retrofit.create(GitHubJobsService::class.java)
    }
}