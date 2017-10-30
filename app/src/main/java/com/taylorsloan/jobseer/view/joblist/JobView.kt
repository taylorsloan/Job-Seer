package com.taylorsloan.jobseer.view.joblist

import android.content.Context
import android.widget.LinearLayout
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.data.model.Job
import io.nlopez.smartadapters.views.BindableLinearLayout
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view_job.view.*


/**
 * Created by taylo on 10/29/2017.
 */
class JobView(context: Context) : BindableLinearLayout<Job>(context) {

    override fun onViewInflated() {
        super.onViewInflated()
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun getLayoutId(): Int {
        return R.layout.item_view_job
    }

    override fun bind(p0: Job?) {
        p0?.let {
            textViewJobName.text = it.title
        }
    }

    override fun getOrientation(): Int {
        return LinearLayout.HORIZONTAL
    }
}