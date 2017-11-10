package com.taylorsloan.jobseer.view.joblist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.view.jobdetail.JobDetailActivity
import com.taylorsloan.jobseer.view.joblist.model.Loading
import io.nlopez.smartadapters.SmartAdapter
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter
import io.nlopez.smartadapters.utils.ViewEventListener
import kotlinx.android.synthetic.main.activity_job_list.*
import timber.log.Timber


class JobListActivity : AppCompatActivity(), JobListContract.View, ViewEventListener<Job>{

    companion object {
        const val KEY_LIST_STATE = "recyclerViewState"
    }

    private val loadingMarker = Loading()
    private var isShowingLoading = false
    var loadingPosition = 0

    private lateinit var presenter: JobListContract.Presenter
    private lateinit var adapter: RecyclerMultiAdapter

    private lateinit var items : ArrayList<Any>

    private var listState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)
        listState = getListState(savedInstanceState)
        setupViews()
        setupInteractions()
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

    private fun setupViews(){
        presenter = JobListPresenter(this)
        items = ArrayList(60)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SmartAdapter.items(items)
                .map(Job::class.java, JobView::class.java)
                .map(Loading::class.java, LoadingView::class.java)
                .listener(this)
                .into(recyclerView)
        val scrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Timber.d("Load Page: %s", page)
                presenter.loadMore(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun setupInteractions(){
        swipeRefreshLayout.setOnRefreshListener {
            presenter.refresh()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onStop() {
        super.onStop()
        recyclerView.clearOnScrollListeners()
    }

    override fun showLoading() {

        frameLayout_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        frameLayout_loading.visibility = View.GONE
    }

    private fun findLastVisibleView() : View{
        val lm = recyclerView.layoutManager as LinearLayoutManager
        val lastVisiblePosition = lm.findLastVisibleItemPosition()
        return lm.findViewByPosition(lastVisiblePosition)
    }

    override fun showJobDetail(job: Job) {
        job.id?.let {
            JobDetailActivity.startActivity(this, it)
        }
    }

    override fun hideRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        listState = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()
        outState?.putParcelable(KEY_LIST_STATE, listState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        listState = getListState(savedInstanceState)
    }

    fun getListState(savedInstanceState: Bundle?) : Parcelable?{
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
