package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.model.Job_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Created by taylo on 10/28/2017.
 */
class JobPersistor(dataModule: DataModule) : ObservableTransformer<List<Job>, List<Job>> {

    init {
        dataModule.storageComponent().inject(this)
        initJobBox()
    }

    @Inject
    lateinit var boxStore : BoxStore

    private lateinit var jobBox : Box<Job>

    private fun initJobBox(){
        jobBox = boxStore.boxFor(Job::class.java)
    }

    override fun apply(upstream: Observable<List<Job>>): ObservableSource<List<Job>> {
        return upstream.map {
            it.forEach {
                val savedJob = jobBox.query().equal(Job_.id, it.id!!).build().findUnique()
                it.dbId = savedJob?.dbId ?: 0
            }
            jobBox.put(it)
            it
        }
    }
}