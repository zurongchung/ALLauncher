package stayalive.ollie.com.allanucher

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import stayalive.ollie.com.allanucher.activity.BaseActivity
import stayalive.ollie.com.allanucher.fragment.HomeFragment

class HomeActivity : BaseActivity(), HomeFragment.OnFragmentInteractionListener {
    override val logTag: String
        get() = "Home activity"
    private fun getLayout(): Int {
        return R.layout.activity_home
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        viewPager.adapter = HomePagerAdapter(supportFragmentManager)
    }

    private class HomePagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            return HomeFragment.newInstance("argA", "argB")
        }

        override fun getCount(): Int {
            return 3
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        // do something
    }

}
