package stayalive.ollie.com.allanucher.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    protected abstract val logTag: String
    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(logTag, "[ ON CREATE ]")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(logTag, "[ ON CREATE VIEW ]")
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onPause() {
        super.onPause()
        Log.v(logTag, "[ ON PAUSE ]")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(logTag, "[ ON ATTACH ]")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(logTag, "[ ON DETACH ]")
    }

}