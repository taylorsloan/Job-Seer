package com.taylorsloan.jobseer.view.joblist.browse

import android.os.Bundle
import android.view.View
import com.taylorsloan.jobseer.view.joblist.common.AbstractJobListFragment
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import kotlinx.android.synthetic.main.fragment_job_list.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.isEnabled = false
    }
}