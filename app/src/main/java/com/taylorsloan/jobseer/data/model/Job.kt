package com.taylorsloan.jobseer.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

/**
 * Created by taylorsloan on 10/28/17.
 */
@Entity
data class Job (@Id var dbId : Long = 0,
                @Index var id: String?,
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