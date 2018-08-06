package stayalive.ollie.com.allanucher

import android.os.Bundle
import android.util.Log
import stayalive.ollie.com.allanucher.activity.BaseActivity
class HomeActivity :
    BaseActivity() {
    override val logTag: String
        get() = "Home activity"

    private fun getLayout() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        Log.v(logTag, "[ ON CREATE ]")
    }
}