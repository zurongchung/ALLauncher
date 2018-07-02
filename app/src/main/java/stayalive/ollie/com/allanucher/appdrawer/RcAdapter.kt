package stayalive.ollie.com.allanucher.appdrawer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.appview.view.*
import stayalive.ollie.com.allanucher.R

const val logTag = "Rc adapter"


class RcAdapter(private val dataSource: MutableList<AppInfo>) :
    RecyclerView.Adapter<RcAdapter.AppViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val inflateView = LayoutInflater.from(parent.context)
            .inflate(R.layout.appview, parent, false)
        return AppViewHolder(inflateView)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        var source: AppInfo
        for (i in dataSource) {
            source = dataSource[position]
            holder.bindViewWithData(source)
        }

    }

    override fun getItemCount(): Int = dataSource.size

    class AppViewHolder(v: View) :
        RecyclerView.ViewHolder(v),
        View.OnClickListener
    {
        private var view = v
        private lateinit var info: AppInfo
        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val ctx: Context = v?.context!!
            val i: Intent = ctx.packageManager.getLaunchIntentForPackage(info.appPkgName)
            ctx.startActivity(i)
        }
        fun bindViewWithData(appInfo: AppInfo) {
            info = appInfo
            view.app_icon.setImageDrawable(appInfo.appIcon)
            view.app_label.text = appInfo.appLabel
        }

    }

}