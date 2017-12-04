package com.taylorsloan.jobseer.view.joblist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
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

    private lateinit var adapter : MyAdapter
    private val disposables = CompositeDisposable()

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
        adapter = MyAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private fun clearFilters() {
        editText_location.setText("")
    }

    private fun setupInteractions() {
        disposables.add(RxCompoundButton.checkedChanges(toggleButton_filter)
                .subscribe {
                    if (it) {
                        constraintLayout_filter.expand()
                    } else {
                        constraintLayout_filter.collapse()
                        clearFilters()
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
                    propagateSearchParams(it.first.toString(), it.second.toString(), it.third)
                }))
    }

    private fun propagateSearchParams(description: String, location: String, fullTime: Boolean) {
        (0..adapter.count)
                .map { supportFragmentManager.findFragmentByTag("android:switcher:${R.id.viewPager}:$it") as? JobListContract.View }
                .forEach { page ->
                    page?.searchJobs(description,
                            location,
                            fullTime)
                }
    }

    private fun tearDownInteractions() {
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        tearDownInteractions()
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
