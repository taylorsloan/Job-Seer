package com.taylorsloan.jobseer.view.joblist

import android.support.v7.util.DiffUtil
import com.taylorsloan.jobseer.data.model.Job

/**
 * Created by taylo on 10/29/2017.
 */
class JobDiffUtilCallback(private val oldJobs : List<Job>,
                          private val newJobs : List<Job>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldId = oldJobs[oldItemPosition].id
        val newId = newJobs[newItemPosition].id
        return oldId == newId
    }

    override fun getOldListSize(): Int {
        return oldJobs.size
    }

    override fun getNewListSize(): Int {
        return newJobs.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJob = oldJobs[oldItemPosition].copy(dbId = 0)
        val newJob = newJobs[newItemPosition].copy(dbId = 0)
        val oldHash = oldJob.hashCode()
        val newHash = newJob.hashCode()
        return oldJob.hashCode() == newJob.hashCode()
    }
}