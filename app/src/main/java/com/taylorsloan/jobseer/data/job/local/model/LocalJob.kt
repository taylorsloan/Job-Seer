package com.taylorsloan.jobseer.data.job.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Model that describes a job that will be saved to local DB
 * Created by taylorsloan on 10/28/17.
 */
@Entity(tableName = "jobs")
data class LocalJob(@PrimaryKey(autoGenerate = false) var id: String?,
                    var created_at: String?,
                    var title: String?,
                    var location: String?,
                    var type: String?,
                    var description: String?,
                    var how_to_apply: String?,
                    var company: String?,
                    var company_url: String?,
                    var company_logo: String?,
                    var url: String?,
                    var saved: Boolean? = false)