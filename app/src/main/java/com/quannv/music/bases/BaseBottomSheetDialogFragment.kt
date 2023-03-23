package com.quannv.music.bases

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.quannv.music.R
import com.quannv.music.customizes.FontCache
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.utilities.commons.Constants
import com.quannv.music.utilities.commons.MessageStatus
import com.quannv.music.views.dialogs.NotifyDialog

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    open lateinit var rootView: VB
    private val isHideKeyboardOnStartUp: Boolean
        get() = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog?
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val behavior = BottomSheetBehavior.from(
                    bottomSheet
                )
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = getViewBinding(inflater)
        val view = rootView.root
//        view.setOnTouchListener(this)
        observeHandle()
        setupView()
        setupEventControl()

        view.isClickable = true
        view.isFocusableInTouchMode = true

        return rootView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): VB

    @SuppressLint("ClickableViewAccessibility")
    open fun setupView() {
        rootView.root.setOnClickListener {
            hideKeyboard()
        }
    }

    open fun setupEventControl() {

    }

    open fun observeHandle() {

    }

    open fun bindData() {

    }

    private fun hideKeyboard() {
        val view: View? = this.dialog?.currentFocus
        if ((view != null) && (context != null) && (view !is EditText)) {
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
            view.clearFocus()
        }
    }

//    override fun onDismiss(dialogInterface: DialogInterface) {
//        try {
//            if (Utils.shared.checkKeyboardVisible())
//                activity?.let { Utils.shared.showHideKeyBoard(it, false) }
//        } catch (ignored: Exception) {
//        }
//    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun showSnackBar(
        status: MessageStatus, message: String = "",
        isInfinitive: Boolean = false
    ): Snackbar {
        val duration = if (isInfinitive)
            Snackbar.LENGTH_INDEFINITE
        else
            Snackbar.LENGTH_SHORT
        val snackbar = Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            message,
            duration
        )
        val sbView = snackbar.view
        val layoutParams: FrameLayout.LayoutParams = sbView.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        when (status) {
            MessageStatus.ERROR -> {
                sbView.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.red_400))
            }
            MessageStatus.SUCCESS -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.color_snackbar_success
                    )
                )
            }
            MessageStatus.INFO -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.color_snackbar_info
                    )
                )
            }
            MessageStatus.WARNING -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        activity!!,
                        R.color.color_snackbar_warning
                    )
                )
            }
        }
        sbView.layoutParams = layoutParams
        val textView: TextView = sbView
            .findViewById(com.google.android.material.R.id.snackbar_text)
        textView.setPadding(0, 2, 0, 2)
        textView.gravity = Gravity.CENTER
        textView.typeface = FontCache.getTypeface(activity!!, Constants.DEFAULT_FONT)
        textView.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
        textView.clickWithDebounce { snackbar.dismiss() }
        snackbar.show()
        return snackbar
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
    ) {
        context?.let {
            NotifyDialog.instance(it)
                .setTitle(title)
                .setMessage(message)
                .show()
        }
    }

}