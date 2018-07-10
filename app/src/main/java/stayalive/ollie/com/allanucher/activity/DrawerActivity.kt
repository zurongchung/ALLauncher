package stayalive.ollie.com.allanucher.activity

import android.os.Bundle
import stayalive.ollie.com.allanucher.R

class DrawerActivity : BaseActivity() {
    override val logTag = "DrawerActivity"
    private fun getLayout(): Int {
        return R.layout.activity_drawer
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }
}
