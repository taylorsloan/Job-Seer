package com.taylorsloan.jobseer.view.joblist.browse

import android.support.v7.widget.RecyclerView
import com.taylorsloan.jobseer.view.joblist.common.AbstractJobListFragment
import com.taylorsloan.jobseer.view.joblist.common.EndlessRecyclerViewScrollListener
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import kotlinx.android.synthetic.main.fragment_job_list.*
import timber.log.Timber

/**
 * Created by taylorsloan on 11/10/17.
 */
class BrowseJobListFragment : AbstractJobListFragment() {

    companion object {
        fun newInstance(): BrowseJobListFragment {
            return BrowseJobListFragment()
        }
    }
    private lateinit var scrollListener : EndlessRecyclerViewScrollListener

    override fun providePresenter(): JobListContract.Presenter {
        return BrowseJobListPresenter(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.clearOnScrollListeners()
    }

    override fun setupViews(){
        super.setupViews()
        scrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Timber.d("Load Page: %s", page)
                presenter.loadMore(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun setupInteractions(){
        swipeRefreshLayout.setOnRefreshListener {
            scrollListener.resetState()
            presenter.refresh()

        }
    }
}