package stayalive.ollie.com.allanucher.fragment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.view.Window
import android.view.WindowManager

class MainScreenLifecycleObserver : LifecycleObserver {
    private val logTag = "Main_Screen_observer"

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun openAppDrawer() {
        // nothing yet
    }
}