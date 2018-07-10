package stayalive.ollie.com.allanucher.fragment.deprecated

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import android.widget.Toast
import stayalive.ollie.com.allanucher.R
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.AppManager
import stayalive.ollie.com.allanucher.appdrawer.RcAdapter
import stayalive.ollie.com.allanucher.appdrawer.Shortcut
import stayalive.ollie.com.allanucher.appdrawer.shortcut.ShortcutListAdapter

private const val ARG_PARAM = "param"

class AppDrawerFragment : Fragment() {
    private val logTag = "App_drawer_fragment"
    private var param: String? = null
    private fun getLayout() = R.layout.fragment_app_drawer
    private lateinit var appDrawerContainer: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var appManager: AppManager
    private lateinit var shortcutPopContainer: RecyclerView
    private var shortcutPopup: PopupWindow? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appManager = AppManager(context)
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

        viewManager = GridLayoutManager(context,
            drawer_col
        )
        val viewAdapter = RcAdapter(appManager)
        viewAdapter.itemLongClickListener = {appInfo, itemView ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                showPopup(appInfo, itemView)
            }
            /**
             * @return true
             * @OnClick will invoke if return false
             */
            true
        }
        appDrawerContainer = view.findViewById<RecyclerView>(R.id.app_drawer_view_recycle).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun showPopup(app: AppInfo, itemView: View): Boolean {
        val shortcuts = appManager.getShortcutFromApp(app.appPkgName)
        if (shortcuts.isNotEmpty()) {
            val contentView = createShortcutListView(shortcuts)
            val location = IntArray(2){0}
            itemView.getLocationOnScreen(location)
            shortcutPopup?.dismiss()
            shortcutPopup = PopupWindow(contentView, WRAP_CONTENT, WRAP_CONTENT, true)
            shortcutPopup?.apply {
                animationStyle = R.style.Animation_AppCompat_Dialog
                showAtLocation(itemView, Gravity.NO_GRAVITY,
                    location[0] + itemView.width / 2,
                    location[1] + itemView.height / 2)
            }
        } else {
            Toast.makeText(
                context,
                "This app has no shortcuts or you are not a launcher",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun createShortcutListView(shortcuts: List<Shortcut>): View {
        val view = LayoutInflater.from(context).inflate(R.layout.shortcut_popup_container, null)
        shortcutPopContainer = view.findViewById(R.id.shortcutPopupRoot)
        shortcutPopContainer.apply {
            setHasFixedSize(true)
            adapter = ShortcutListAdapter(shortcuts, appManager)
            layoutManager = LinearLayoutManager(context)
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