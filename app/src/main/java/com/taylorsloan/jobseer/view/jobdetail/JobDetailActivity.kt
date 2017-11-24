package com.taylorsloan.jobseer.view.jobdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job
import com.taylorsloan.jobseer.view.util.RoundedCornersTransformation

import kotlinx.android.synthetic.main.activity_job_detail.*

class JobDetailActivity : AppCompatActivity(), JobDetailContract.View,
        AppBarLayout.OnOffsetChangedListener {

    companion object {

        private const val KEY_JOB_ID = "jobId"
        private const val PERCENTAGE_TO_ANIMATE_AVATAR = 20


        fun startActivity(context: Context, jobId: String){
            val intent = Intent(context, JobDetailActivity::class.java)
            intent.putExtra(KEY_JOB_ID, jobId)
            context.startActivity(intent)
        }
    }

    private lateinit var jobId : String
    private lateinit var presenter : JobDetailContract.Presenter

    private var maxScrollSize = 0
    private var isAvatarShown = true

    private var googleMap : GoogleMap? = null
    private var isSaved  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        jobId = getJobIdFromIntent()
        presenter = JobDetailPresenter(this, jobId)
        mapView.onCreate(savedInstanceState)
        setupViews()
        setupInteractions()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    private fun setupViews(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appBarLayout.addOnOffsetChangedListener(this)
        maxScrollSize = appBarLayout.totalScrollRange
        mapView.getMapAsync { initMap(it) }
        view_mapBlock.setOnTouchListener { _, _ -> true } // Ignore All Touch Events on map view
    }

    private fun setupInteractions(){
        fab.setOnClickListener { view ->
            Snackbar.make(view, R.string.activity_job_detail_open_website, Snackbar.LENGTH_LONG)
                    .setAction(R.string.activity_job_detail_open, { presenter.openApplicationPage()}).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_job_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.action_save ->{
                item.isChecked = true
                true
            }

            R.id.action_share -> {
                presenter.openShareJobDialog()
                true
            }

            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initMap(map: GoogleMap){
        googleMap = map
    }

    private fun getJobIdFromIntent() : String{
        return intent.getStringExtra(KEY_JOB_ID)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(KEY_JOB_ID, jobId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        jobId = savedInstanceState?.getString(KEY_JOB_ID) ?: ""
    }

    override fun showJob(job: Job) {
        textView_jobName.text = job.title
        textView_companyName.text = job.company
        textView_location.text = job.location
        Picasso.with(this).load(job.company_logo)
                .transform(RoundedCornersTransformation(10, 0))
                .fit()
                .centerInside()
                .into(imageView_companyIcon)
        html_text.setHtml(job.description ?: "")
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
        mapView.onPause()
    }

    override fun showCompanyWebPage(url: String) {
        val uri = Uri.parse(Uri.decode(url))
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        startActivity(intent)
    }

    override fun showCompanyWebPageLoadError() {
        Toast.makeText(this, R.string.activity_job_detail_url_error, Toast.LENGTH_SHORT).show()
    }

    override fun showLocation(location: Pair<Double, Double>) {
        frameLayout_map.visibility = View.VISIBLE
        frameLayout_remote.visibility = View.GONE
        val marker = LatLng(location.first, location.second)
        googleMap?.addMarker(MarkerOptions().position(marker))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap?.moveCamera(CameraUpdateFactory.zoomTo(10.0f))
    }

    override fun showRemoteLocation() {
        frameLayout_map.visibility = View.GONE
        frameLayout_remote.visibility = View.VISIBLE
    }

    override fun showShareJobDialog(company: String, position: String, link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out this job for a " +
                "$position at $company. Apply Here: $link")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, resources
                .getText(R.string.activity_job_detail_share)))
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (maxScrollSize == 0)
            maxScrollSize = appBarLayout?.totalScrollRange ?: 0

        val percentage = Math.abs(verticalOffset) * 100 / maxScrollSize

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && isAvatarShown) {
            isAvatarShown = false

            imageView_companyIcon.animate()
                    .scaleY(0f).scaleX(0f)
                    .setDuration(200)
                    .start()
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !isAvatarShown) {
            isAvatarShown = true

            imageView_companyIcon.animate()
                    .scaleY(1f).scaleX(1f)
                    .start()
        }
    }
}
