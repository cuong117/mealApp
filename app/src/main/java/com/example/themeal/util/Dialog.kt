package com.example.themeal.util

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class Dialog(
    private val builder: AlertDialog.Builder
) {

    private var _dialog: AlertDialog? = null

    fun showRemoveDialog(
        title: String,
        message: String,
        onPositive: (DialogInterface, Int) -> Unit
    ) {
        if (_dialog == null || _dialog?.isShowing == false) {
            builder.apply {
                setTitle(title)
                setMessage(message)
                setCancelable(true)
                setNegativeButton(TITLE_CANCEL) { dialog, _ ->
                    dialog.cancel()
                }
                setPositiveButton(TITLE_ACCEPT) { dialog, which ->
                    onPositive(dialog, which)
                }
            }
            _dialog = builder.create()
            _dialog?.show()
        }
    }

    companion object {
        private const val TITLE_CANCEL = "Cancel"
        private const val TITLE_ACCEPT = "Ok"
    }
}
