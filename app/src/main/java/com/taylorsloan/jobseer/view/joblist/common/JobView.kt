package com.taylorsloan.jobseer.view.joblist.common

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.domain.job.models.Job
import io.nlopez.smartadapters.views.BindableLinearLayout
import kotlinx.android.synthetic.main.item_view_job.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by taylo on 10/29/2017.
 */
class JobView(context: Context) : BindableLinearLayout<Job>(context) {

    private val dateFormat =  SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH)
    private val datePrinter = SimpleDateFormat("MM/dd/yy", Locale.ENGLISH)

    companion object {
        const val ACTION_SELECTED = 100
    }

    override fun onViewInflated() {
        super.onViewInflated()
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        setupInteractions()

    }

    override fun getLayoutId(): Int {
        return R.layout.item_view_job
    }

    override fun bind(p0: Job?) {
        p0?.let {
            Picasso.with(context).load(it.company_logo)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.ic_business)
                    .into(imageView_companyIcon)

            val date = dateFormat.parse(it.created_at)

            textView_jobName.text = it.title
            textView_jobType.text = it.type
            textView_companyName.text = it.company
            textView_location.text = it.location
            textView_postDate.text = datePrinter.format(date)

        }
    }

    override fun getOrientation(): Int {
        return LinearLayout.HORIZONTAL
    }

    private fun setupInteractions(){
        row.setOnClickListener {
            notifyItemAction(ACTION_SELECTED)
        }
    }
}