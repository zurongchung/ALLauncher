package stayalive.ollie.com.allanucher.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.AppManager

class InstalledAppViewModel : ViewModel() {
    private var mApps: MutableLiveData<List<AppInfo>>? = null

    fun getApps(context: Context): LiveData<List<AppInfo>> {
        if (mApps == null) {
            mApps = MutableLiveData()
            loadInstalledApps(context)
        }
        return mApps as MutableLiveData<List<AppInfo>>
    }

    private fun loadInstalledApps(context: Context) {
        mApps?.value = AppManager(context).getLaunchableApps()
    }
}