package com.quannv.music.views.dialogs

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.quannv.music.bases.BaseDialog
import com.quannv.music.databinding.DialogNotifyBinding
import com.quannv.music.utilities.setViewClickListener


class NotifyDialog private constructor(context: Context) :
    BaseDialog<DialogNotifyBinding>(context, R.style.Theme_Translucent_NoTitleBar) {
    private var onListenerCallback: ((isOK: Boolean) -> Unit)? = null
    fun setListener(listener: ((isOK: Boolean) -> Unit)?): NotifyDialog {
        this.onListenerCallback = listener
        return this
    }

    override fun foundView() {
        rootView.btnOK.setViewClickListener { clickOk() }
        rootView.btnCancel.setViewClickListener { clickCancel() }
    }

    fun setTitle(title: String?): NotifyDialog {
        rootView.tvTitle.text = title
        rootView.tvTitle.isVisible = !title.isNullOrEmpty()
        return this
    }

    fun setMessage(message: String?): NotifyDialog {
        rootView.tvMessage.text = message
        rootView.tvMessage.isVisible = !message.isNullOrEmpty()
        return this
    }

    override fun notFoundView() {

    }

    override fun getViewBinding(): DialogNotifyBinding {
        return DialogNotifyBinding.inflate(layoutInflater)
    }

    fun enableOk(isEnable: Boolean): NotifyDialog {
        if (isEnable) rootView.btnOK.visibility = View.VISIBLE else rootView.btnOK.visibility =
            View.GONE
        return this
    }

    fun enableCancel(isEnable: Boolean): NotifyDialog {
        if (isEnable) rootView.btnCancel.visibility =
            View.VISIBLE else rootView.btnCancel.visibility =
            View.GONE
        return this
    }

    fun clickOk() {
        dismiss()
        if (onListenerCallback != null)
            onListenerCallback!!.invoke(true)
    }

    fun clickCancel() {
        dismiss()
        if (onListenerCallback != null)
            onListenerCallback!!.invoke(false)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mNotifyDialog: NotifyDialog? = null

        fun instance(context: Context): NotifyDialog {
            if (mNotifyDialog != null) {
                mNotifyDialog!!.dismiss()
                mNotifyDialog = null
            }
            mNotifyDialog = NotifyDialog(context)
            return mNotifyDialog!!
        }
    }
}
