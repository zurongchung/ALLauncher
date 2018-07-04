package stayalive.ollie.com.allanucher.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_app_drawer.*
import stayalive.ollie.com.allanucher.R
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.RcAdapter
import stayalive.ollie.com.allanucher.helper.AutoFitGridLayoutManager

private const val ARG_PARAM = "param"

class AppDrawerFragment : Fragment() {
    private val logTag = "App_drawer_fragment"
    private var param: String? = null
    private fun getLayout() = R.layout.fragment_app_drawer
    private lateinit var appDrawerContainer: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var appInfoList: MutableList<AppInfo>
    private lateinit var pm: PackageManager

    private fun loadApps() {
        val availableActivities: MutableList<ResolveInfo>
        var app: AppInfo
        var icon: Drawable
        var label: CharSequence
        var pkgName: String
        appInfoList = mutableListOf()
        pm = activity?.packageManager!!
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        availableActivities = pm.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            icon = ri.activityInfo.loadIcon(pm)
            pkgName = ri.activityInfo.packageName
            label = ri.loadLabel(pm)
            app = AppInfo(icon, label, pkgName)
            appInfoList.add(app)
        }
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadApps()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }

        Log.v(logTag, "[ ON CREATE ]")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(logTag, "[ ON CREATE VIEW ]")
        val view =  inflater.inflate(getLayout(), container, false)

        viewManager = GridLayoutManager(context, drawer_col)
        viewAdapter = RcAdapter(appInfoList)
        appDrawerContainer = view.findViewById<RecyclerView>(R.id.app_drawer_view_recycle).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        Log.v(logTag, "[ ON RESUME ]")
    }

    override fun onPause() {
        super.onPause()
        Log.v(logTag, "[ ON RESUME ]")
    }

    companion object {
        // changeable by user setting a new value
        var drawer_col: Int = 5
        @JvmStatic
        fun newInstance(param: String) =
                AppDrawerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM, param)
                    }
                }
    }
}