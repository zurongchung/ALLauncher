package stayalive.ollie.com.allanucher.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_app_drawer.*
import stayalive.ollie.com.allanucher.R
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.AppManager
import stayalive.ollie.com.allanucher.appdrawer.RcAdapter
import stayalive.ollie.com.allanucher.shortcuts.ShortcutPop
import stayalive.ollie.com.allanucher.viewModel.InstalledAppViewModel

class DrawerActivity : BaseActivity() {
    override val logTag = "DrawerActivity"
    private fun getLayout(): Int {
        return R.layout.activity_drawer
    }
    private lateinit var appManager: AppManager
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mAppsModel: InstalledAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        appManager = AppManager(this)
        //createAppsLayout()
        init()
        //val height = getStatusBarHeight()
        Log.v(logTag, "[ ON CREATE ]")
    }
    private fun init() {
        mAppsModel = ViewModelProviders.of(this).get(InstalledAppViewModel::class.java)
        val vmObserver: Observer<List<AppInfo>> = Observer {
            createAppsLayout(it)
        }
        mAppsModel.getApps(this).observe(this, vmObserver)
    }
    private fun createAppsLayout(apps: List<AppInfo>?) {
        viewManager = GridLayoutManager(this, drawer_col)
        val viewAdapter = RcAdapter(apps, appManager)
        viewAdapter.itemLongClickListener = {appInfo, itemView ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                ShortcutPop.showPopup(this, appManager, appInfo, itemView)
            }
            /**
             * @return true
             * @OnClick will invoke if return false
             */
            true
        }
        app_drawer_view_recycle.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    /**
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun showPopup(app: AppInfo, itemView: View): Boolean {
        val shortcuts = appManager.getShortcutFromApp(app.appPkgName)
        if (shortcuts.isNotEmpty()) {
            val contentView = ShortcutPop.createShortcutListView(this, appManager, shortcuts)
            val location = IntArray(2){0}
            itemView.getLocationOnScreen(location)
            shortcutPopup?.dismiss()
            shortcutPopup = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
            shortcutPopup?.apply {
                animationStyle = R.style.Animation_AppCompat_Dialog
                showAtLocation(itemView, Gravity.NO_GRAVITY,
                    location[0] + itemView.width / 2,
                    location[1] + itemView.height / 2)
            }
        } else {
            Toast.makeText(
                this,
                "This app has no shortcuts or you are not a launcher",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun createShortcutListView(shortcuts: List<Shortcut>): View {
        val view = LayoutInflater.from(this).inflate(R.layout.shortcut_popup_container, null)
        shortcutPopContainer = view.findViewById(R.id.shortcutPopupRoot)
        shortcutPopContainer.apply {
            //setHasFixedSize(true)
            adapter = ShortcutListAdapter(shortcuts, appManager)
            layoutManager = LinearLayoutManager(context)
        }
        return view
    }
    **/
    fun getStatusBarHeight(): Int {
        var result = 0
        val resId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = resources.getDimensionPixelSize(resId)
        }
        return result
    }
    companion object {
        // changeable by user setting a new value
        var drawer_col = 5
    }
}
