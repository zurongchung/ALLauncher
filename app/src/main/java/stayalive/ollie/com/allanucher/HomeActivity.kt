package stayalive.ollie.com.allanucher

import android.net.Uri
import android.os.Bundle
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

        val homeFragment: HomeFragment = HomeFragment.newInstance("argA", "argB")
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, homeFragment)
            commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        // do something
    }

}
