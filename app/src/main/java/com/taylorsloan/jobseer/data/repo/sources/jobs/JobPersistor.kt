package com.taylorsloan.jobseer.data.repo.sources.jobs

import com.taylorsloan.jobseer.data.DataModule
import com.taylorsloan.jobseer.data.model.DataResult
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.model.Job_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by taylo on 10/28/2017.
 */
class JobPersistor(dataModule: DataModule) {

    private val consumerSubject = PublishSubject.create<DataResult<List<Job>>>().toSerialized()

    init {
        dataModule.storageComponent().inject(this)
        initJobBox()
    }

    @Inject
    lateinit var boxStore : BoxStore

    private var initialized = false

    private lateinit var jobBox : Box<Job>

    var disposable : Disposable? = null

    private fun initJobBox(){
        jobBox = boxStore.boxFor(Job::class.java)
    }

    fun init(){
        initialized = true
        consumerSubject.subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            boxStore.runInTx {
                                it.data?.let {
                                    it.forEach {
                                        val savedJob = jobBox.query().
                                                equal(Job_.id, it.id!!)
                                                .build()
                                                .findUnique()
                                        it.dbId = savedJob?.dbId ?: 0
                                    }
                                    jobBox.put(it)
                                }
                            }
                        },
                        {
                            Timber.e(it, "Could not save jobs")
                        },
                        {},
                        {
                            disposable = it
                        }
                )
    }

    fun tearDown(){
        initialized = false
        disposable?.dispose()
    }

    fun persist(jobs : List<Job>){
        if (initialized)
        consumerSubject.onNext(DataResult(data = jobs))
    }
}