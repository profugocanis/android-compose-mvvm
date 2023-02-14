package com.example.composemvvm.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

object KeyboardManager : LifecycleObserver {

    enum class KeyboardStatus { OPEN, CLOSE }

    private var viewTreeObserverMap = mutableMapOf<String, ViewTreeObserver>()
    private var globalListenerMap = mutableMapOf<String, ViewTreeObserver.OnGlobalLayoutListener>()
    private var statusChangeListenerMap = mutableMapOf<String, (KeyboardStatus) -> Unit>()
    private var isKeyboardShowing = false

    fun hideKeyBoard(activity: Activity?) {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View? = activity.currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun hideKeyBoard(view: View?) {
        val imm: InputMethodManager =
            view?.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyBoard(et: EditText) {
        val inputMethodManager =
            et.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.RESULT_UNCHANGED_SHOWN,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        et.requestFocus()
        inputMethodManager.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    fun addStatusChangeListener(fragment: Fragment, callbackStatus: (KeyboardStatus) -> Unit) {
        val fragmentName = fragment.viewLifecycleOwner.toString()
        statusChangeListenerMap[fragmentName] = callbackStatus
        fragment.lifecycle.addObserver(this)
        val rootView = fragment.requireView()
        val globalListener = createGlobalListener(rootView)
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalListenerMap[fragmentName])
        rootView.viewTreeObserver.addOnGlobalLayoutListener(globalListener)
        viewTreeObserverMap[fragmentName] = rootView.viewTreeObserver
        globalListenerMap[fragmentName] = globalListener
    }

    private fun createGlobalListener(rootView: View) = ViewTreeObserver.OnGlobalLayoutListener {
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val screenHeight: Int = rootView.rootView.height
        val keypadHeight: Int = screenHeight - r.bottom
        isKeyboardShowing = if (keypadHeight > screenHeight * 0.15) {
            if (!isKeyboardShowing) sendStatus(KeyboardStatus.OPEN)
            true
        } else {
            if (isKeyboardShowing) sendStatus(KeyboardStatus.CLOSE)
            false
        }
    }

    private fun sendStatus(status: KeyboardStatus) {
        try {
            statusChangeListenerMap.forEach {
                it.value(status)
            }
        } catch (e: Exception) {
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onStop(owner: LifecycleOwner) {
        viewTreeObserverMap[owner.toString()]?.removeOnGlobalLayoutListener(globalListenerMap[owner.toString()])
    }
}