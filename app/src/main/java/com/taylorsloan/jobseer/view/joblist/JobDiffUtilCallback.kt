package com.taylorsloan.jobseer.view.joblist

import android.support.v7.util.DiffUtil
import com.taylorsloan.jobseer.data.model.Job

/**
 * Created by taylo on 10/29/2017.
 */
class JobDiffUtilCallback(private val oldJobs : List<Any>,
                          private val newJobs : List<Any>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJob = oldJobs[oldItemPosition] as? Job
        val newJob = newJobs[newItemPosition] as? Job
        if (oldJob == null || newJob == null) return false
        return oldJob.id == newJob.id
    }

    override fun getOldListSize(): Int {
        return oldJobs.size
    }

    override fun getNewListSize(): Int {
        return newJobs.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJob = oldJobs[oldItemPosition] as? Job
        val newJob = newJobs[newItemPosition] as? Job
        if (oldJob == null || newJob == null) return false
        val oldJobCopy = oldJob.copy(dbId = 0)
        val newJobCopy = newJob.copy(dbId = 0)
        return oldJobCopy.hashCode() == newJobCopy.hashCode()
    }
}