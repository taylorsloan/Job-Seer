package com.taylorsloan.jobseer.view.joblist

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import com.taylorsloan.jobseer.R
import com.taylorsloan.jobseer.view.joblist.model.Loading
import io.nlopez.smartadapters.views.BindableLinearLayout

/**
 * Created by taylorsloan on 10/31/17.
 */
class LoadingView(context: Context) : BindableLinearLayout<Loading>(context) {

    override fun onViewInflated() {
        super.onViewInflated()
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun getLayoutId(): Int {
        return R.layout.item_view_loading
    }

    override fun bind(p0: Loading?){}

    override fun getOrientation(): Int {
        return LinearLayout.HORIZONTAL
    }
}