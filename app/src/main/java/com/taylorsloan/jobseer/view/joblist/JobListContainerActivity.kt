package com.taylorsloan.jobseer.view.joblist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.lapism.searchview.SearchView
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.view.joblist.browse.BrowseJobListFragment
import com.taylorsloan.jobseer.view.joblist.browse.SavedJobListFragment
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import kotlinx.android.synthetic.main.activity_job_list_container.*

class JobListContainerActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private const val FRAGMENT_TAG_GITHUB_JOB = "github"
        private const val FRAGMENT_TAG_SAVED = "saved"
    }

    private var jobListView : JobListContract.View? = null

    private val items = ArrayList<Pair<String, Fragment>>(2)
    private lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list_container)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText))
        setupViews()
        setupInteractions()
    }

    private fun setupViews() {
        tabLayout.setupWithViewPager(viewPager)
        initBrowseJobsFragment()
        initSavedJobFragment()
        adapter = MyAdapter(supportFragmentManager, items)
        viewPager.adapter = adapter
    }

    private fun setupInteractions() {
//        searchView.setOnQueryTextListener(this)
    }

    private fun tearDownInteractions() {
//        searchView.setOnQueryTextListener(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        tearDownInteractions()
    }

    private fun initBrowseJobsFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_GITHUB_JOB)
        if (fragment == null) {
            fragment = BrowseJobListFragment.newInstance()
        }
        jobListView = fragment as? JobListContract.View
        items.add(Pair("Browse", fragment))
    }

    private fun initSavedJobFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_SAVED)
        if (fragment == null) {
            fragment = SavedJobListFragment.newInstance()
        }
        items.add(Pair("Saved", fragment))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            jobListView?.searchJobs(query!!)

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    class MyAdapter(fm: FragmentManager, val item: ArrayList<Pair<String, Fragment>>) :
            FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return item[position].second
        }

        override fun getCount(): Int {
            return item.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return item[position].first
        }
    }
}
