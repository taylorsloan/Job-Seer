package com.taylorsloan.jobseer.view.joblist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job
import io.nlopez.smartadapters.SmartAdapter
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter
import kotlinx.android.synthetic.main.activity_job_list.*

class JobListActivity : AppCompatActivity(), JobListContract.View{

    private lateinit var presenter: JobListContract.Presenter
    private lateinit var adapter: RecyclerMultiAdapter

    private lateinit var items : ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)
        setupViews()
        setupInteractions()
    }

    override fun showJobs(jobs: List<Job>) {
        val result = DiffUtil.calculateDiff(JobDiffUtilCallback(items, jobs))
        items.clear()
        items.addAll(jobs)
        result.dispatchUpdatesTo(adapter)
    }

    private fun setupViews(){
        presenter = JobListPresenter(this)
        items = ArrayList(60)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SmartAdapter.items(items)
                .map(Job::class.java, JobView::class.java)
                .into(recyclerView)
        val scrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadMore(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }



    private fun setupInteractions(){

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
}
