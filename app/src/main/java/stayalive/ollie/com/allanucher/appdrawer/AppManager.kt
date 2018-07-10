package stayalive.ollie.com.allanucher.appdrawer

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Process
import android.support.annotation.RequiresApi
import android.util.Log

typealias ShortcutQuery = LauncherApps.ShortcutQuery

class AppManager(private val context: Context?) {
    private val logTag = "AppMan"
    private val packageManager = context?.packageManager!!
    private val launcherApps: LauncherApps
        get() = context?.getSystemService((Context.LAUNCHER_APPS_SERVICE)) as LauncherApps

    fun getLaunchableApps(): List<AppInfo> {
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(i, 0)
            .map{
                it.activityInfo
            }
            .map {
                AppInfo(
                    it.loadIcon(packageManager),
                    it.loadLabel(packageManager).toString(),
                    it.packageName
                    ) }
    }

    fun startApp(app: AppInfo) {
        context?.startActivity(packageManager.getLaunchIntentForPackage(app.appPkgName))
    }

    /**
     * Use only for system version
     * @Build.VERSION_CODE.N_MR1 >= 25
     **/
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun getShortcutFromApp(packageName: String): List<Shortcut> {
        val shortcutQuery: ShortcutQuery? = LauncherApps.ShortcutQuery()
        shortcutQuery?.apply {
            setQueryFlags(
                ShortcutQuery.FLAG_MATCH_MANIFEST or
                ShortcutQuery.FLAG_MATCH_DYNAMIC or
                ShortcutQuery.FLAG_MATCH_PINNED
            )
            setPackage(packageName)
        }
        return try {
            launcherApps.getShortcuts(shortcutQuery, Process.myUserHandle())
                .map {
                    Shortcut(it.id, it.shortLabel.toString(), it.`package`, loadShortcutIcon(it))
                }
        } catch (e: SecurityException) {
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun startShortcut(shortcut: Shortcut) {
        launcherApps.startShortcut(shortcut.packageName, shortcut.id, null, null, Process.myUserHandle())

    }
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun loadShortcutIcon(shortcutInfo: ShortcutInfo): Drawable? {
        return try {
            launcherApps.getShortcutIconDrawable(
                shortcutInfo,
                context?.resources?.displayMetrics!!.densityDpi)
        }catch (e: IllegalStateException) {
            Log.e(logTag, e.message)
            null
        }
    }

}