package stayalive.ollie.com.allanucher

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import stayalive.ollie.com.allanucher.activity.BaseActivity
import stayalive.ollie.com.allanucher.fragment.HomeFragment

class HomeActivity : BaseActivity() {
    override val logTag: String
        get() = "Home activity"
    private fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        viewPager.adapter = HomePagerAdapter(supportFragmentManager)
        // may cause problems
        viewPager.currentItem = 1
        Log.v(logTag, "[ ON CREATE ]")
    }

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
}
