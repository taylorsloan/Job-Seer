package com.taylorsloan.jobseer.data

import com.taylorsloan.jobseer.dagger.component.DaggerNetComponent
import com.taylorsloan.jobseer.dagger.module.NetModule

/**
 * Created by taylorsloan on 10/28/17.
 */
object DataModule {

    const val GITHUB_JOBS_URL  = "https://jobs.github.com"

    val netComponent = DaggerNetComponent.builder()
            .netModule(NetModule(GITHUB_JOBS_URL))
            .build()
}