package com.taylorsloan.jobseer.view.joblist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.lapism.searchview.SearchHistoryTable
import com.lapism.searchview.SearchItem
import com.lapism.searchview.SearchView
import com.taylorsloan.jobseer.R
import kotlinx.android.synthetic.main.activity_job_list_container.*

class JobListContainerActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_TAG_GITHUB_JOB = "github"
    }

    private lateinit var historyDatabase : SearchHistoryTable

    private var jobListView : JobListContract.View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list_container)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText))
        setupViews()
        setupInteractions()
    }

    private fun setupViews(){
        initJobListFragment()
//        historyDatabase = SearchHistoryTable(this)
    }

    private fun setupInteractions(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                historyDatabase.addItem(SearchItem(query))
                if (!query.isNullOrEmpty()){
                    jobListView?.searchJobs(query!!)
                    searchView.close(false)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {

                }
                return false
            }
        })
    }

    private fun initJobListFragment(){
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_GITHUB_JOB)
        if (fragment == null){
            fragment = JobListFragment.newInstance()
        }
        jobListView = fragment as? JobListContract.View
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout_container, fragment, FRAGMENT_TAG_GITHUB_JOB)
                .commit()
    }
}
