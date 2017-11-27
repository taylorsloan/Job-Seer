package com.taylorsloan.jobseer.data.job.util

import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.data.job.net.model.NetJob
import com.taylorsloan.jobseer.domain.job.models.Job

/**
 * Adapter used to map objects received from online sources to DB sources and vice-versa
 * Created by taylorsloan on 11/26/17.
 */
object JobMapper {

    fun map(job: LocalJob): NetJob {
        return NetJob(job.id,
                job.created_at,
                job.title, job.location,
                job.type, job.description,
                job.how_to_apply,
                job.company,
                job.company_url,
                job.company_logo,
                job.url)
    }

    fun toPlainObj(job: LocalJob): Job {
        return Job(job.id,
                job.created_at,
                job.title, job.location,
                job.type, job.description,
                job.how_to_apply,
                job.company,
                job.company_url,
                job.company_logo,
                job.url,
                job.saved)
    }

    fun convertLocalToPlainObj(jobs: List<LocalJob>): List<Job> {
        val convertedJobs = ArrayList<Job>(jobs.size)
        jobs.forEach {
            val job = toPlainObj(it)
            convertedJobs.add(job)
        }
        return convertedJobs
    }

    fun map(job: NetJob): LocalJob {
        return LocalJob(job.id,
                job.created_at,
                job.title, job.location,
                job.type, job.description,
                job.how_to_apply,
                job.company,
                job.company_url,
                job.company_logo,
                job.url)
    }

    fun toPlainObj(job: NetJob): Job {
        return Job(job.id,
                job.created_at,
                job.title, job.location,
                job.type, job.description,
                job.how_to_apply,
                job.company,
                job.company_url,
                job.company_logo,
                job.url)
    }

    fun convertNetToPlainObj(jobs: List<NetJob>): List<Job> {
        val convertedJobs = ArrayList<Job>(jobs.size)
        jobs.forEach {
            val job = toPlainObj(it)
            convertedJobs.add(job)
        }
        return convertedJobs
    }
}