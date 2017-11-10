package com.taylorsloan.jobseer.view.jobdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job

import kotlinx.android.synthetic.main.activity_job_detail.*
import timber.log.Timber

class JobDetailActivity : AppCompatActivity(), JobDetailContract.View {

    companion object {

        private const val  KEY_JOB_ID = "jobId"

        fun startActivity(context: Context, jobId: String){
            val intent = Intent(context, JobDetailActivity::class.java)
            intent.putExtra(KEY_JOB_ID, jobId)
            context.startActivity(intent)
        }
    }

    lateinit var jobId : String
    lateinit var presenter : JobDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        setSupportActionBar(toolbar)
        jobId = getJobIdFromIntent()
        presenter = JobDetailPresenter(this, jobId)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Toast.makeText(this, "Received Job Id: $jobId", Toast.LENGTH_SHORT).show()
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
        Timber.d("Recieved a job: ${job.title}")
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }
}
