package stayalive.ollie.com.allanucher.shortcuts

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import stayalive.ollie.com.allanucher.R
import stayalive.ollie.com.allanucher.appdrawer.AppInfo
import stayalive.ollie.com.allanucher.appdrawer.AppManager

class ShortcutPop {
    companion object {
        @RequiresApi(Build.VERSION_CODES.N_MR1)
        fun showPopup(
            context: Context,
            appManager: AppManager,
            app: AppInfo,
            itemView: View): Boolean
        {
            val shortcuts = appManager.getShortcutFromApp(app.appPkgName)
            if (shortcuts.isNotEmpty()) {
                val contentView = createShortcutListView(context, appManager, shortcuts)
                val shortcutPopup: PopupWindow?
                val location = IntArray(2){0}
                itemView.getLocationOnScreen(location)
                shortcutPopup = PopupWindow(
                    contentView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true)
                shortcutPopup.apply {
                    animationStyle = R.style.Animation_AppCompat_Dialog
                    showAtLocation(itemView, Gravity.NO_GRAVITY,
                        location[0] + itemView.width / 2,
                        location[1] + itemView.height / 2)
                }
            } else {
                Toast.makeText(
                    context,
                    "This app has no shortcuts or you are not a launcher",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return true

        }

        fun createShortcutListView(context: Context, appManager: AppManager, shortcuts: List<Shortcut>): View {
            val view = LayoutInflater.from(context).inflate(R.layout.shortcut_popup_container, null)
            view.findViewById<RecyclerView>(R.id.shortcutPopupRoot).apply {
                //setHasFixedSize(true)
                adapter = ShortcutListAdapter(
                    shortcuts,
                    appManager
                )
                layoutManager = LinearLayoutManager(context)
            }
            return view
        }
    }
}