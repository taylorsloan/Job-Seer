package com.taylorsloan.jobseer.view.joblist

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.lapism.searchview.SearchView
import com.taylorsloan.jobseer.R
import kotlinx.android.synthetic.main.activity_job_list_container.*

class JobListContainerActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private const val FRAGMENT_TAG_GITHUB_JOB = "github"
    }

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
    }

    private fun setupInteractions(){
        searchView.setOnQueryTextListener(this)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            jobListView?.searchJobs(query!!)
            searchView.close(false)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}
