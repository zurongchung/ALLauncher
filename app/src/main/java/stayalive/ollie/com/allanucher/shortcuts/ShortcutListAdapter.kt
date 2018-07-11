package stayalive.ollie.com.allanucher.shortcuts

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shortcut_popup.view.*
import stayalive.ollie.com.allanucher.R
import stayalive.ollie.com.allanucher.appdrawer.AppManager

class ShortcutListAdapter(
    private val shortcuts: List<Shortcut>,
    private val appManager: AppManager) :
    RecyclerView.Adapter<ShortcutListAdapter.ShortcutViewHolder>()
{

    //var itemOnClickListener: (Shortcut, PopupWindow) -> Boolean = {_, _ -> false}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortcutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shortcut_popup, parent, false)
        return ShortcutViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShortcutViewHolder, position: Int) {
        shortcuts.forEach { _ ->
            holder.bindViewData(shortcuts[position])
        }
    }

    override fun getItemCount(): Int = shortcuts.size

    inner class ShortcutViewHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {

        private val view = v
        private lateinit var mShortcut: Shortcut

        init {
            v.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.N_MR1)
        override fun onClick(v: View?) {
            appManager.startShortcut(mShortcut)
        }

        fun bindViewData(shortcut: Shortcut) {
            mShortcut = shortcut
            view.shortcut_label.text = shortcut.label
            view.shortcut_icon.setImageDrawable(shortcut.icon)
        }
    }
}