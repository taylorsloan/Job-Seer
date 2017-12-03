package com.taylorsloan.jobseer.view.joblist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.view.collapse
import com.taylorsloan.jobseer.view.expand
import com.taylorsloan.jobseer.view.joblist.browse.BrowseJobListFragment
import com.taylorsloan.jobseer.view.joblist.browse.SavedJobListFragment
import com.taylorsloan.jobseer.view.joblist.common.JobListContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_job_list_container.*
import timber.log.Timber
import java.util.concurrent.TimeUnit



class JobListContainerActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_TAG_GITHUB_JOB = "github"
        private const val FRAGMENT_TAG_SAVED = "saved"
    }

    private var jobListView : JobListContract.View? = null

    private val items = ArrayList<Pair<String, Fragment>>(2)
    private lateinit var adapter : MyAdapter
    private val disposables = CompositeDisposable()

    private var filterExpanded = false

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
//        initBrowseJobsFragment()
//        initSavedJobFragment()
        adapter = MyAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private fun setupInteractions() {
        disposables.add(RxView.clicks(imageButton_filter)
                .subscribe {
                    constraintLayout_filter.apply {
                        filterExpanded = if (filterExpanded){
                            collapse()
                            false
                        } else {
                            expand()
                            true
                        }
                    }
                })
        disposables.add(Observable.combineLatest(RxTextView.textChanges(editText_search),
                RxTextView.textChanges(editText_location),
                RxCompoundButton.checkedChanges(checkBox_fullTime),
                Function3<CharSequence, CharSequence, Boolean,
                        Triple<CharSequence, CharSequence, Boolean>> {
                    search, location, fullTime ->
                    Triple(search, location, fullTime)
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Query: %s, Location: %s, Fulltime: %s", it.first, it.second, it.third)
                    (0..adapter.count)
                            .map { supportFragmentManager.findFragmentByTag("android:switcher:${R.id.viewPager}:$it") as? JobListContract.View }
                            .forEach { page ->
                                page?.searchJobs(it.first.toString(),
                                        it.second.toString(),
                                        it.third)
                            }
                }))
    }

    private fun tearDownInteractions() {
        disposables.clear()
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

    class MyAdapter(fm: FragmentManager) :
            FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> BrowseJobListFragment.newInstance()
                1 -> SavedJobListFragment.newInstance()
                else -> throw RuntimeException("Fragment does not exist at this position")
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Browse"
                1 -> "Saved"
                else -> throw RuntimeException("Fragment does not exist at this position")
            }
        }
    }
}
