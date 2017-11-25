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
//        initJobListFragment()
        items.add(Pair("Browse", BrowseJobListFragment.newInstance()))
        items.add(Pair("Saved", SavedJobListFragment.newInstance()))
        adapter = MyAdapter(supportFragmentManager, items)
        viewPager.adapter = adapter
    }

    private fun setupInteractions() {
        searchView.setOnQueryTextListener(this)
    }

    private fun initJobListFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_GITHUB_JOB)
        if (fragment == null) {
            fragment = BrowseJobListFragment.newInstance()
        }
        jobListView = fragment as? JobListContract.View
        /*supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout_container, fragment, FRAGMENT_TAG_GITHUB_JOB)
                .commit()*/
    }

    private fun initSavedJobFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_SAVED)
        if (fragment == null) {
            fragment = BrowseJobListFragment.newInstance()
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            jobListView?.searchJobs(query!!)
            searchView.close(false)
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
