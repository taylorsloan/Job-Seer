package com.taylorsloan.jobseer.data.job.local.service

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import io.reactivex.Flowable

/**
 * Data Access Object that defines how the db can be accessed
 * Created by taylorsloan on 11/26/17.
 */
@Dao
interface JobDao {

    /**
     * Insert Jobs into the local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJobs(vararg jobs: LocalJob)

    @Query("SELECT * FROM jobs")
    fun loadAllJobs(): Flowable<List<LocalJob>>
}