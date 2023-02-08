package com.example.composemvvm.extentions

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.example.composemvvm.R

fun Context.showInfo(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

private var dialogAsk: AlertDialog? = null

fun Context.showAskDialog(
    title: String? = null,
    msg: String? = null,
    okTitle: String? = null,
    okCallback: (() -> Unit)? = null,
    cancelCallback: (() -> Unit)? = null
): AlertDialog? {
    try {
        if (dialogAsk != null) return null
        val okText = okTitle ?: "Ok"
        val builder = AlertDialog.Builder(this)
            .setTitle(title ?: getString(R.string.app_name))
            .setMessage(msg)
            .setCancelable(false)
            .setNegativeButton("Cancel") { _, _ ->
                cancelCallback?.invoke()
                dialogAsk = null
            }
            .setPositiveButton(okText) { _, _ ->
                okCallback?.invoke()
                dialogAsk = null
            }
        dialogAsk = builder.show()
    } catch (e: Exception) {
    }
    return dialogAsk
}

fun Context.showInfoDialog(
    msg: CharSequence? = null,
    okButtonText: String = "Ok",
    title: String? = null,
    okCallback: (() -> Unit)? = null
): AlertDialog? {
    try {
        if (dialogAsk != null) return null
        val builder = AlertDialog.Builder(this)
            .setTitle(title ?: getString(R.string.app_name))
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(okButtonText) { _, _ ->
                okCallback?.invoke()
                dialogAsk = null
            }
        dialogAsk = builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return dialogAsk
}

fun Context.dismissDialog() {
    try {
        dialogAsk?.dismiss()
        dialogAsk = null
    } catch (e: Exception) {
    }
}

fun Context.copyTextToBuffer(text: String?) {
    val clipboard: ClipboardManager? =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("text", text)
    clipboard?.setPrimaryClip(clip)
}