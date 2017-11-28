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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertJobs(jobs: List<LocalJob>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertJob(job: LocalJob)

    @Query("SELECT * FROM jobs WHERE saved = :saved ORDER BY date_added ASC")
    fun loadJobs(saved: Int = 0) : Flowable<List<LocalJob>>

    @Query("SELECT * FROM jobs WHERE id = :id")
    fun loadJob(id: String) : Flowable<LocalJob>

    @Query("UPDATE jobs SET saved = :saved WHERE id = :id")
    fun saveJob(saved: Int = 1, id: String) : Int

    @Query("DELETE FROM jobs WHERE saved = 0")
    fun clearJobs()
}