package stayalive.ollie.com.allanucher.fragment

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

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
        Log.i(logTag, "the position get from pager is: $param1")
        if (param1 == 1) {
            val parentID = R.id.frag_home_contents_container
            val floatID = R.id.home_control_but
            val p = view.findViewById<ConstraintLayout>(parentID)
            val c = LayoutInflater.from(context).inflate(R.layout.home_central_button, null)
            p.addView(c)

            val constraintSet = ConstraintSet()
            constraintSet.clone(p)
            constraintSet.apply {
                connect(floatID, ConstraintSet.END, parentID, ConstraintSet.END, 0)
                connect(floatID, ConstraintSet.START, parentID, ConstraintSet.START, 0)
                connect(floatID, ConstraintSet.BOTTOM, parentID, ConstraintSet.BOTTOM, 20)
                applyTo(p)
            }
            Log.v(logTag, "control button successfully attached.")
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val floatBut =
            frag_home_contents_container.findViewById<FloatingActionButton>(R.id.home_control_but)
        floatBut?.let {
            it.setOnClickListener {
                Log.i(logTag, " Floating button has been pressed. working")
                /**
                 *  Fragments never talk to each other directly
                 *  open app drawer
                 *  needed to talk to activity
                 *  let activity to open drawer
                 */
                activity?.supportFragmentManager.apply {
                    this!!.beginTransaction()
                        .replace(R.id.appDrawerContainer,
                            AppDrawerFragment.newInstance("appDrawer replace"),
                            "App drawer fragment")
                        .addToBackStack(null)
                        .commit()
                }

            }
        }
        Log.v(logTag, "ON ACTIVITY CREATED")
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
