package stayalive.ollie.com.allanucher

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_central_button.*
import stayalive.ollie.com.allanucher.activity.BaseActivity
import stayalive.ollie.com.allanucher.activity.DrawerActivity
import stayalive.ollie.com.allanucher.fragment.HomeFragment

class HomeActivity : BaseActivity() {
    override val logTag: String
        get() = "Home activity"
    private fun getLayout(): Int {
        return R.layout.activity_home
    }

    private fun getTransStatusBar() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getTransStatusBar()
        setContentView(getLayout())
        touchFloatButtonOpenDrawer()
        /**
        viewPager.apply {
            adapter = HomePagerAdapter(supportFragmentManager)
            // may cause problems
            currentItem = 1
        }
        **/

        Log.v(logTag, "[ ON CREATE ]")
    }

    private fun touchFloatButtonOpenDrawer() {
        val i = Intent(this, DrawerActivity::class.java)
        home_control_but.let {
            it.setOnClickListener {
                Log.i(logTag, " Floating button has been pressed. working")
                startActivity(i)
            }
        }
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
