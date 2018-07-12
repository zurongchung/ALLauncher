package stayalive.ollie.com.allanucher

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.math.MathUtils
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import stayalive.ollie.com.allanucher.activity.BaseActivity
import stayalive.ollie.com.allanucher.activity.DrawerActivity
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.AppManager
import stayalive.ollie.com.allanucher.shortcuts.ShortcutPop
import stayalive.ollie.com.allanucher.viewModel.InstalledAppViewModel
import kotlin.math.abs

class HomeActivity :
    BaseActivity()
{
    override val logTag: String
        get() = "Home activity"
    private fun getLayout(): Int {
        return R.layout.activity_home
    }
    private lateinit var appManager: AppManager
    private lateinit var mAppsModel: InstalledAppViewModel
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getTransStatusBar()
        setContentView(getLayout())
        init()
        /**
        viewPager.apply {
            adapter = HomePagerAdapter(supportFragmentManager)
            // may cause problems
            currentItem = 1
        }
        **/

        Log.v(logTag, "[ ON CREATE ]")
    }

    private fun init() {
        appManager = AppManager(this)
        touchFloatButtonOpenDrawer()
        mDetector = GestureDetectorCompat(this, GesturesOnHomeScreenListener())
        mAppsModel = ViewModelProviders.of(this).get(InstalledAppViewModel::class.java)
        val vmObserver: Observer<List<AppInfo>> = Observer {
            initDock(it)
        }
        mAppsModel.getApps(this).observe(this, vmObserver)
    }

    private fun touchFloatButtonOpenDrawer() {
        val i = getOpenDrawerIntent()
        home_control_but.let {
            it.setOnClickListener {
                startActivity(i)
            }
        }
    }
    fun getOpenDrawerIntent(): Intent {
        return Intent(this, DrawerActivity::class.java)
    }

    private fun initDock(apps: List<AppInfo>?) {
        val dockApps = findDockApps(apps)
        dockApps?.let {
            for ((index, app) in it.withIndex()) {
                dock_container.getChildAt(index).apply {
                    background = app.appIcon
                    contentDescription = app.appLabel
                    setOnClickListener {
                        startActivity(packageManager.getLaunchIntentForPackage(app.appPkgName))
                    }
                    setOnLongClickListener {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                            ShortcutPop.showPopup(context, appManager, app, it)
                        }
                        true
                    }
                }
                Log.d(logTag, "found: [ $index, ${app.appPkgName} ]")
            }
        }
    }

    private fun findDockApps(apps: List<AppInfo>?): List<AppInfo>? {
        val dialerRegx = Regex("""\.dialer$""")
        val mmsRegx = Regex("""\.mms$""")
        val cameraRegx = Regex("""\.camera$""")
        val browserRegx = Regex("""\.browser$""")
        val matchedApps: List<AppInfo>?
        matchedApps = apps?.filter {
            dialerRegx.containsMatchIn(it.appPkgName) or
                    mmsRegx.containsMatchIn(it.appPkgName) or
                    cameraRegx.containsMatchIn(it.appPkgName) or
                    browserRegx.containsMatchIn(it.appPkgName)
        }
        return matchedApps
    }

    fun handleTouchEvent(v: View, event: MotionEvent?): Boolean {
        val actionCode: Int? = event?.actionMasked
        Log.d(logTag, "action: [ $actionCode ], maskAction: [ ${event?.actionMasked} ]")
        when (actionCode) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(logTag, "action: DOWN")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(logTag, "action: MOVE")
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.d(logTag, "action: UP")
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(logTag, "action: CANCEL")
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(logTag, "action: OUTSIDE")
                return true
            }
            else -> return false
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    inner class GesturesOnHomeScreenListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            var deltaY = 0.0f
            e1?.let {
                e2?.let {
                    deltaY = abs(e1.y - e2.y)
                }
            }
            if (deltaY > 300) {
                startActivity(getOpenDrawerIntent())
            }
            return true
        }
    }
    /** used with pager
    private class HomePagerAdapter(manager: FragmentManager
    ) : FragmentStatePagerAdapter(manager) {

        override fun getItem(position: Int): Fragment {
            Log.d("pagerAdpt", "position: [ $position ]")
            return HomeFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return 3
        }
    }
    **/
}
