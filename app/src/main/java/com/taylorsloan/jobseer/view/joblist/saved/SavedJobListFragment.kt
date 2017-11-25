package com.taylorsloan.jobseer.view.joblist.browse

import com.taylorsloan.jobseer.view.joblist.common.AbstractJobListFragment
import com.taylorsloan.jobseer.view.joblist.common.JobListContract

/**
 * Created by taylorsloan on 11/10/17.
 */
class SavedJobListFragment : AbstractJobListFragment() {

    companion object {
        fun newInstance() : SavedJobListFragment {
            return SavedJobListFragment()
        }
    }

    override fun providePresenter(): JobListContract.Presenter {
        return SavedJobListPresenter(this)
    }
}