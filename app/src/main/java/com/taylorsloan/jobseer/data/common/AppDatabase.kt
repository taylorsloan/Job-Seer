package com.taylorsloan.jobseer.data.common

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.data.job.local.service.JobDao

/**
 * Database using Room
 * Created by taylorsloan on 11/26/17.
 */
@Database(entities = arrayOf(LocalJob::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
}