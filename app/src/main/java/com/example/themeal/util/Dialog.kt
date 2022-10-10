package com.example.themeal.util

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.example.themeal.R

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

    fun showLoadingDialog() {
        if (_dialog == null) {
            builder.setView(R.layout.loading)
                .setCancelable(false)
            _dialog = builder.create()
            _dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            _dialog?.show()
        }
    }

    fun dismiss() {
        if (_dialog?.isShowing == true) {
            _dialog?.dismiss()
        }
    }

    fun showDisconnectNetworkDialog(title: String, message: String) {
        if (_dialog == null) {
            builder.apply {
                setTitle(title)
                setMessage(message)
                setCancelable(true)
                setNegativeButton(TITLE_ACCEPT) { dialog, _ ->
                    dialog.cancel()
                }
            }
            _dialog = builder.create()
            _dialog?.show()
        } else {
            _dialog?.show()
        }
    }

    companion object {
        private const val TITLE_CANCEL = "Cancel"
        private const val TITLE_ACCEPT = "Ok"
    }
}
