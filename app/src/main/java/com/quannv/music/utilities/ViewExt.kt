package com.quannv.music.utilities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.ColorStateList
import android.os.SystemClock
import android.text.InputFilter
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.quannv.music.R


//region setting animation for view
fun View.fadeOut(alpha: Float = 0.0f, duration: Long = 1000) {
    animate()
        .alpha(alpha)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
}

fun View.fadeIn(alpha: Float = 1.0f, duration: Long = 1000) {
    setAlpha(0.0f)
    visibility = View.VISIBLE
    animate()
        .alpha(alpha).duration = duration
}

fun View.enterFromBottom(duration: Long = 300) {
    val animate = TranslateAnimation(
        0.0f,  // fromXDelta
        0.0f,  // toXDelta
        height.toFloat(),  // fromYDelta
        0.0f
    ) // toYDelta

    animate.duration = duration
    animate.fillAfter = true
    startAnimation(animate)
}

fun View.exitToBottom(duration: Long = 300, onFinish: (() -> Unit)) {
    val animate = TranslateAnimation(
        0.0f,  // fromXDelta
        0.0f,  // toXDelta
        0.0f,  // fromYDelta
        height.toFloat()
    ) // toYDelta

    animate.duration = duration
    animate.fillAfter = true
    animate.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            onFinish()
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }
    })
    startAnimation(animate)
}

fun ImageView.changeResourceAnim(image: Int) {
    val animOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    val animIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    animOut.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            setImageResource(image)
            animIn.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            startAnimation(animIn)
        }
    })
    startAnimation(animOut)
}

fun TextView.changeTextColorAnim(toColor: Int) {
    val animOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    val animIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    animOut.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            setTextColor(toColor)
            animIn.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            startAnimation(animIn)
        }
    })
    startAnimation(animOut)
}

fun View.performClick() {
    val SCALE_VAL = 0.85f
    val DURATION_ANIM: Long = 500
    animate()
        .scaleX(SCALE_VAL)
        .scaleY(SCALE_VAL)
        .setDuration(DURATION_ANIM)
        .setInterpolator(LinearInterpolator())
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                performClick()
            }

            override fun onAnimationCancel(p0: Animator) {
                performClick()
            }

            override fun onAnimationRepeat(p0: Animator) {

            }
        }).start()

}

fun View.setViewClickListener(listener: View.OnClickListener?) {
    val SCALE_VAL = 0.85f
    val DURATION_ANIM: Long = 150
    fun reverseScaleView(view: View) {
        animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(DURATION_ANIM)
            .setInterpolator(LinearInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    listener?.onClick(view)
                }

                override fun onAnimationCancel(p0: Animator) {
                    listener?.onClick(view)
                }

                override fun onAnimationRepeat(p0: Animator) {

                }
            }).start()

    }

    setOnClickListener {
        animate()
            .scaleX(SCALE_VAL)
            .scaleY(SCALE_VAL)
            .setDuration(DURATION_ANIM)
            .setInterpolator(LinearInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}

                override fun onAnimationEnd(p0: Animator) {
                    reverseScaleView(it)
                }

                override fun onAnimationCancel(p0: Animator) {
                    reverseScaleView(it)
                }

                override fun onAnimationRepeat(p0: Animator) {}
            }).start()

    }
}

//endregion


fun View.hideKeyboard() {
    val imm = context?.getSystemService<InputMethodManager>()
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard(andRequestFocus: Boolean = false) {
    if (andRequestFocus) {
        requestFocus()
    }
    val imm = context?.getSystemService<InputMethodManager>()
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

val View.keyboardIsVisible: Boolean
    get() = WindowInsetsCompat
        .toWindowInsetsCompat(rootWindowInsets)
        .isVisible(WindowInsetsCompat.Type.ime())

fun View.getString(@StringRes resourceId: Int): String =
    context.getString(resourceId)

fun View.getColor(@ColorRes colorRes: Int): Int =
    context.getColor(colorRes)

fun View.getColorStateList(@ColorRes colorRes: Int): ColorStateList =
    context.getColorStateList(colorRes)

fun Fragment.getColor(@ColorRes colorRes: Int): Int =
    requireContext().getColor(colorRes)

fun EditText.onlyInputNumberDecimal() {
    val blockChar = "₫,~`!@#$%^&*()_-+=<>?/{}[]|:';\""
    this.filters = arrayOf(
        InputFilter { cs, i1, i2, spanned, _, _ ->
            if (cs == " ") { // for backspace
                return@InputFilter ""
            }
            if (cs.toString() == "." && (spanned.toString().isEmpty() || spanned.toString()
                    .contains("."))
            ) {
                return@InputFilter ""
            }
            if (cs.toString() == ",") {
                if (spanned.toString().isEmpty() || spanned.toString().contains(".")) {
                    return@InputFilter ""
                } else {
                    return@InputFilter "."
                }
            }
            if (cs.toString() == "-") {
                return@InputFilter ""
            }
            if (cs != null && blockChar.contains("" + cs)) {
                return@InputFilter ""
            }
            for (j in i1 until i2) {
                if (Character.isLetter(cs[j]) || Character.isWhitespace(cs[j])) {
                    return@InputFilter ""
                }
            }
            return@InputFilter cs
        })
}

fun EditText.onlyInputNumber() {
    val blockChar = ".,₫,~`!@#$%^&*()_-+=<>?/{}[]|:';\""
    this.filters = arrayOf(
        InputFilter { cs, i1, i2, spanned, _, _ ->
            if (cs == " ") { // for backspace
                return@InputFilter ""
            }
            if (cs != null && blockChar.contains("" + cs)) {
                return@InputFilter ""
            }
            for (j in i1 until i2) {
                if (Character.isLetter(cs[j]) || Character.isWhitespace(cs[j])) {
                    return@InputFilter ""
                }
            }
            return@InputFilter cs
        })
}

fun EditText.getTextWithTrim(): String =
    this.text.toString().trim()


private var mLastClickTime: Long = 0

fun View.clickWithDebounce(debounceTime: Long = 500L, action: () -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - mLastClickTime < debounceTime) {
            return@setOnClickListener
        }
        action()
        mLastClickTime = SystemClock.elapsedRealtime()
    }
}

fun LinearLayout.addDotsPages(count: Int) {
    var imageView: ImageView
    for (i in 0 until count) {
        imageView = ImageView(this.context)
        //Set icon dot default
        imageView.setImageResource(R.drawable.dot_normal_page)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //custom margin
        params.setMargins(
            2.dpToPx(this.context),
            0,
            2.dpToPx(this.context),
            0
        )
        imageView.layoutParams = params
        this.addView(imageView)
    }
    updateDotsPagerTransaction(0)
}

fun LinearLayout.updateDotsPagerTransaction(currentPosition: Int) {
    try {
        for (i in 0 until this.childCount) {
            if (this.getChildAt(i) is ImageView) {
                (this.getChildAt(i) as ImageView).setImageResource(R.drawable.dot_normal_page)
            }
        }
        //Set the chosen dot on position
        (this.getChildAt(currentPosition) as ImageView).setImageResource(R.drawable.dot_selected_page)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
