package com.taylorsloan.jobseer.data.job.util

import com.taylorsloan.jobseer.data.job.local.model.LocalJob
import com.taylorsloan.jobseer.data.job.net.model.NetJob
import com.taylorsloan.jobseer.domain.job.models.Job

/**
 * Adapter used to mapToNet objects received from online sources to DB sources and vice-versa
 * Created by taylorsloan on 11/26/17.
 */
object JobMapper {

    fun mapToNet(job: LocalJob): NetJob {
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

    fun mapToNet(job: Job): NetJob {
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

    fun mapToLocal(job: Job): LocalJob {
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

    fun mapToLocal(job: NetJob): LocalJob {
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

    fun mapToDomain(job: LocalJob): Job {
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

    fun mapToDomain(job: NetJob): Job {
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

    fun mapLocalListToDomain(jobs: List<LocalJob>): List<Job> {
        val convertedJobs = ArrayList<Job>(jobs.size)
        jobs.forEach {
            val job = mapToDomain(it)
            convertedJobs.add(job)
        }
        return convertedJobs
    }

    fun mapNetListToDomain(jobs: List<NetJob>): List<Job> {
        val convertedJobs = ArrayList<Job>(jobs.size)
        jobs.forEach {
            val job = mapToDomain(it)
            convertedJobs.add(job)
        }
        return convertedJobs
    }

    fun mapDomainListToLocal(jobs: List<Job>): List<LocalJob> {
        val convertedJobs = ArrayList<LocalJob>(jobs.size)
        jobs.forEach {
            val job = mapToLocal(it)
            convertedJobs.add(job)
        }
        return convertedJobs
    }
}