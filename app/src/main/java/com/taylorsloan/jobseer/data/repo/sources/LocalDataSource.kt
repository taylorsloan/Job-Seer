package com.taylorsloan.jobseer.data.repo.sources

import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.data.model.Job_
import io.objectbox.Box
import io.objectbox.rx.RxQuery
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class LocalDataSource : DataSource {

    @Inject
    lateinit var jobBox : Box<Job>

    override fun jobs(description: String?, location: String?, lat: Double?, long: Double?, fullTime: Boolean?): Observable<List<Job>> {
        return RxQuery.observable(jobBox.query().build())
    }

    override fun job(id: String): Observable<Job> {
        return RxQuery.observable(jobBox.query().equal(Job_.id, id).build())
                .map{ it[0]}
    }
}