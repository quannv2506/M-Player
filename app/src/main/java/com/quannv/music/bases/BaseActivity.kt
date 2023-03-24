package com.quannv.music.bases

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.quannv.music.R
import com.quannv.music.customizes.FontCache
import com.quannv.music.views.dialogs.NotifyDialog
import com.quannv.music.models.EventChangeLanguage
import com.quannv.music.utilities.*
import com.quannv.music.utilities.commons.Constants
import com.quannv.music.utilities.commons.MessageStatus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var snackbar: Snackbar? = null
    open lateinit var binding: VB
    var posTop = 0
    var posBottom = 0

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        window?.changeStatusBarColor(this, R.color.color_menu)
//        window?.lightStatusBar()

        setContentView(binding.root)


        EventBus.getDefault().register(this)

        observeHandle()

        setupView()

        setupEventControl()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun subscribeEventLanguage(eventLanguage: EventChangeLanguage) {
        if (eventLanguage.event == Constants.EVENT_CHANGE_LANGUAGE)
            onLanguageChanged()
    }

    open fun onLanguageChanged() {

    }

    open fun setupEventControl() {

    }

    @SuppressLint("ClickableViewAccessibility")
    open fun setupView() {
        binding.root.setOnTouchListener { view, motionEvent ->
            if ((motionEvent.action == MotionEvent.ACTION_UP) && (view !is EditText))
                if (Utils.shared.checkKeyboardVisible()) {
                    Utils.shared.showHideKeyBoard(this, false)
                }
            return@setOnTouchListener true
        }
    }

    open fun observeHandle() {

    }

    protected abstract fun getViewBinding(): VB

    @SuppressLint("UseRequireInsteadOfGet")
    fun showSnackBar(
        status: MessageStatus,
        message: String = "",
        isInfinitive: Boolean = false
    ): Snackbar {
        val duration = if (isInfinitive)
            Snackbar.LENGTH_INDEFINITE
        else {
            snackbar?.dismiss()
            snackbar = null
            Snackbar.LENGTH_SHORT
        }
        if (snackbar == null)
            snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                message,
                duration
            )
        if (isInfinitive)
            snackbar?.behavior = NoSwipeBehavior()

        val sbView = snackbar?.view
        val layoutParams: FrameLayout.LayoutParams =
            sbView?.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        when (status) {
            MessageStatus.ERROR -> {
                sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.red_400))
            }
            MessageStatus.SUCCESS -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_snackbar_success
                    )
                )
            }
            MessageStatus.INFO -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_snackbar_info
                    )
                )
            }
            MessageStatus.WARNING -> {
                sbView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_snackbar_warning
                    )
                )
            }
        }
        sbView.layoutParams = layoutParams
        val textView: TextView? =
            sbView.findViewById(com.google.android.material.R.id.snackbar_text)
        textView?.setPadding(0, 2, 0, 2)
        textView?.gravity = Gravity.CENTER
        textView?.typeface = FontCache.getTypeface(this, Constants.DEFAULT_FONT)
        textView?.setTextColor(ContextCompat.getColor(this, R.color.white))
        if (!isInfinitive)
            textView?.setViewClickListener { snackbar?.dismiss() }
        else
            snackbar?.setText(message)
        if (snackbar?.isShown == false)
            snackbar?.show()
        return snackbar!!
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
    ) {
        NotifyDialog.instance(this)
            .setTitle(title)
            .setMessage(message)
            .enableCancel(false)
            .show()
    }

    open fun showMessage(
        title: String = "",
        message: String = "",
        listener: ((isOK: Boolean) -> Unit)?,
    ) {
        NotifyDialog.instance(this)
            .setTitle(title)
            .setListener(listener)
            .setMessage(message)
            .enableCancel(false)
            .show()
    }

    open fun showQuestion(
        title: String = "",
        message: String = "",
    ) {
        NotifyDialog.instance(this)
            .setTitle(title)
            .setMessage(message)
            .show()
    }

    open fun showQuestion(
        title: String = "",
        message: String = "",
        listener: ((isOK: Boolean) -> Unit)?,
    ) {
        NotifyDialog.instance(this)
            .setTitle(title)
            .setListener(listener)
            .setMessage(message)
            .show()
    }

    private fun showSettingApp() {
        val i = Intent()
        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:$packageName")
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(i)
    }

    //endregion

    //region Event for change language
    fun setLanguage(language: String) {
        UserDefaults.standard.setSharedPreference(
            Constants.CURRENT_LANGUAGE,
            language
        )
        val locale = Locale(language)
        val dms = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(locale)
        resources.updateConfiguration(conf, dms)
//        Locale.setDefault(currentLanguage)
//        val config = Configuration()
//        config.locale = currentLang        //        ViewCompat.setWindowInsetsAnimationCallback(
//            ,
//            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
//                override fun onProgress(
//                    insets: WindowInsetsCompat,
//                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
//                ): WindowInsetsCompat {
//                    // Find an IME animation.
//
//                    posBottom = (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
//                    rootView.root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                        updateMargins(
//                            top = posTop,
//                            bottom = posBottom
//                        )
//                    }
//
//                    return insets
//                }
//            }
//        )uage
//        applicationContext.resources.updateConfiguration(config, null)
        EventBus.getDefault()
            .post(EventChangeLanguage(Constants.EVENT_CHANGE_LANGUAGE, "lang"))
    }

    val currentLanguage: Locale
        get() {
            val currentLanguage = UserDefaults.standard.getSharedPreference(
                Constants.CURRENT_LANGUAGE,
                "en"
            ) as? String ?: "en"
            return Locale(currentLanguage)
        }

}

internal class NoSwipeBehavior : BaseTransientBottomBar.Behavior() {
    override fun canSwipeDismissView(child: View): Boolean {
        return false
    }
}