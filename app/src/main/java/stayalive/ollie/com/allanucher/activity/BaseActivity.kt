package stayalive.ollie.com.allanucher.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val logTag: String

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         Log.v(logTag, "[ ON CREATE ]")
    }

    override fun onStart() {
        super.onStart()
        Log.v(logTag, "[ ON START ]")
    }

    override fun onResume() {
        super.onResume()
        Log.v(logTag, "[ ON RESUME ]")
    }

    override fun onPause() {
        super.onPause()
        Log.v(logTag, "[ ON PAUSE ]")
    }

    override fun onStop() {
        super.onStop()
        Log.v(logTag, "[ ON STOP ]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(logTag, "[ ON DESTROY ]")
    }
}