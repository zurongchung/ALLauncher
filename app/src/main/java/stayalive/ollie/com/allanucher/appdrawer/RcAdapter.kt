package stayalive.ollie.com.allanucher.appdrawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.appview.view.*
import stayalive.ollie.com.allanucher.R

class RcAdapter(
    apps: List<AppInfo>?,
    private val appManager: AppManager) :
    RecyclerView.Adapter<RcAdapter.AppViewHolder>()
{
    private val mSource = apps?.sortedBy { it.appLabel }
    var itemLongClickListener: (AppInfo, View) -> Boolean = {_, _ -> false}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appview, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        mSource?.forEach { _ ->
            holder.bindViewWithData(mSource[position])
        }

    }

    override fun getItemCount(): Int =  mSource?.size!!


    inner class AppViewHolder(v: View) :
        RecyclerView.ViewHolder(v),
        View.OnClickListener
    {
        private var view = v
        private lateinit var info: AppInfo
        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener{ itemLongClickListener.invoke(info, v) }
        }
        override fun onClick(v: View?) {
            Toast.makeText(v?.context, info.appPkgName, Toast.LENGTH_SHORT).show()
            appManager.startApp(info)
        }
        fun bindViewWithData(appInfo: AppInfo) {
            info = appInfo
            view.app_icon.setImageDrawable(appInfo.appIcon)
            view.app_label.text = appInfo.appLabel
        }

    }

}