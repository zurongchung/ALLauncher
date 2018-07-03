package stayalive.ollie.com.allanucher.helper

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class AutoFitGridLayoutManager(ctx: Context?, cw: Int = 320, spanCount: Int = 6) : GridLayoutManager(ctx, spanCount) {

    private var colWidth: Int = cw

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        val totalSpace = if (orientation == VERTICAL) {
            width - paddingLeft - paddingRight
        } else {
            height - paddingTop - paddingBottom
        }
        val spanCount = totalSpace / colWidth
        Log.d("AutoFit", "span is [ $spanCount ]")
        setSpanCount(spanCount)

        super.onLayoutChildren(recycler, state)
    }
}