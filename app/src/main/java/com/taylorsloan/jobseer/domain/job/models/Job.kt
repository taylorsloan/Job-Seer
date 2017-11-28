package com.taylorsloan.jobseer.domain.job.models

/**
 * Created by taylorsloan on 11/26/17.
 */
data class Job(var id: String,
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