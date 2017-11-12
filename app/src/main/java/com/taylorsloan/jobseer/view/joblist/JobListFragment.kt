package com.taylorsloan.jobseer.view.joblist

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.view.jobdetail.JobDetailActivity
import com.taylorsloan.jobseer.view.joblist.model.Loading
import io.nlopez.smartadapters.SmartAdapter
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter
import io.nlopez.smartadapters.utils.ViewEventListener
import kotlinx.android.synthetic.main.fragment_job_list.*
import timber.log.Timber

/**
 * Created by taylorsloan on 11/10/17.
 */
class JobListFragment : Fragment(), JobListContract.View, ViewEventListener<Job> {

    companion object {
        const val KEY_LIST_STATE = "recyclerViewState"

        fun newInstance() : JobListFragment{
            return JobListFragment()
        }
    }

    private lateinit var presenter: JobListContract.Presenter
    private lateinit var adapter: RecyclerMultiAdapter

    private lateinit var items : ArrayList<Any>

    private var listState : Parcelable? = null
    private lateinit var scrollListener : EndlessRecyclerViewScrollListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_job_list, container, false)
        listState = getListState(savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupInteractions()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        recyclerView.clearOnScrollListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()
        outState.putParcelable(KEY_LIST_STATE, listState)
    }

    private fun setupViews(){
        presenter = JobListPresenter(this)
        items = ArrayList(60)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SmartAdapter.items(items)
                .map(Job::class.java, JobView::class.java)
                .map(Loading::class.java, LoadingView::class.java)
                .listener(this)
                .into(recyclerView)
        scrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Timber.d("Load Page: %s", page)
                presenter.loadMore(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun setupInteractions(){
        swipeRefreshLayout.setOnRefreshListener {
            scrollListener.resetState()
            presenter.refresh()

        }

    }

    override fun showJobs(jobs: List<Job>) {
        val result = DiffUtil.calculateDiff(JobDiffUtilCallback(items, jobs))
        items.clear()
        items.addAll(jobs)
        result.dispatchUpdatesTo(adapter)
        listState?.let {
            recyclerView.layoutManager?.onRestoreInstanceState(listState)
            listState = null
        }
        Timber.d("Job Count: %s", items.size)
    }

    override fun showLoading() {
        frameLayout_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        frameLayout_loading.visibility = View.GONE
    }

    override fun hideRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showJobDetail(job: Job) {
        job.id?.let {
            JobDetailActivity.startActivity(context!!, it)
        }
    }

    private fun getListState(savedInstanceState: Bundle?) : Parcelable?{
        return savedInstanceState?.getParcelable(KEY_LIST_STATE)
    }

    override fun onViewEvent(actionId: Int, item: Job?, position: Int, view: View?) {
        when(actionId){
            JobView.ACTION_SELECTED->{
                item?.let {
                    presenter.openJobDetail(item)
                }
            }
        }
    }
}