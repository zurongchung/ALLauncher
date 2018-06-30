package stayalive.ollie.com.allanucher.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import stayalive.ollie.com.allanucher.R

private const val ARG_PARAM1 = "param1"

class HomeFragment : BaseFragment() {
    override var param1: Int? = null
    override val logTag = "Home fragment"
    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        Log.v(logTag, "[ ON CREATE ]")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(logTag, "[ ON CREATE VIEW ]")
        val view = inflater.inflate(getLayout(), container, false)
        return view
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

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
