package com.quannv.music.bases

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.quannv.music.R
import com.quannv.music.customizes.FontCache
import com.quannv.music.views.dialogs.NotifyDialog
import com.quannv.music.models.EventChangeLanguage
import com.quannv.music.utilities.commons.Constants
import com.quannv.music.utilities.commons.MessageStatus
import com.quannv.music.utilities.Utils
import com.quannv.music.utilities.setViewClickListener
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    open lateinit var binding: VB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun subscribeEventLanguage(eventLanguage: EventChangeLanguage) {
        if (eventLanguage.event.equals(Constants.EVENT_CHANGE_LANGUAGE, true))
            onLanguageChanged()
    }

    open fun onLanguageChanged() {

    }

    open fun bindData() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = getViewBinding(inflater)

        observeHandle()

        setupView()

        setupEventControl()
        return binding.root
    }

    open fun setupEventControl() {

    }

    @SuppressLint("ClickableViewAccessibility")
    open fun setupView() {
        binding.root.setOnTouchListener { view, motionEvent ->
            if ((motionEvent.action == MotionEvent.ACTION_UP) && !(view is EditText))
                activity?.let { Utils.shared.showHideKeyBoard(it, false) }
            return@setOnTouchListener true
        }
    }

    open fun observeHandle() {

    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): VB

    @SuppressLint("UseRequireInsteadOfGet")
    fun showSnackBar(
        status: MessageStatus, message: String = "",
        isInfinitive: Boolean = false
    ): Snackbar {
        val duration = if (isInfinitive)
            Snackbar.LENGTH_INDEFINITE
        else
            Snackbar.LENGTH_SHORT
        val mSnackBar = Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            message,
            duration
        )
        val sbView = mSnackBar.view
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
        textView.setViewClickListener { mSnackBar.dismiss() }
        mSnackBar.show()
        return mSnackBar
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
    ) {
        context?.let {
            NotifyDialog.instance(it)
                .setTitle(title)
                .setMessage(message)
                .enableCancel(false)
                .show()
        }
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
        listener: ((isOK: Boolean) -> Unit)?,
    ) {
        context?.let {
            NotifyDialog.instance(it)
                .setTitle(title)
                .setListener(listener)
                .setMessage(message)
                .enableCancel(false)
                .show()
        }
    }

    open fun showQuestion(
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

    open fun showQuestion(
        title: String = "",
        message: String = "",
        listener: ((isOK: Boolean) -> Unit)?,
    ) {
        context?.let {
            NotifyDialog.instance(it)
                .setTitle(title)
                .setListener(listener)
                .setMessage(message)
                .show()
        }
    }

}
