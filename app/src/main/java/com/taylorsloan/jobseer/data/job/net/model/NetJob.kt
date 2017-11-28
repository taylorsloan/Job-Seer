package com.taylorsloan.jobseer.data.job.net.model

/**
 * Model that describes a job received from online sources
 * Created by taylorsloan on 10/28/17.
 */
data class NetJob(var id: String,
                  var created_at: String?,
                  var title: String?,
                  var location: String?,
                  var type: String?,
                  var description: String?,
                  var how_to_apply: String?,
                  var company: String?,
                  var company_url: String?,
                  var company_logo: String?,
                  var url: String?)