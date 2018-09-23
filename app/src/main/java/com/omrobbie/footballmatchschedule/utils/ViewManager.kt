package com.omrobbie.footballmatchschedule.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewManager
import android.widget.LinearLayout
import com.omrobbie.footballmatchschedule.R
import org.jetbrains.anko.*

fun ViewManager.line() = linearLayout {
    view {
        backgroundColor = Color.LTGRAY
    }.lparams(matchParent, dip(1)) {
        topMargin = dip(8)
    }
}

fun ViewManager.progressBar(idProgressBar: Int) = progressBar {
    id = idProgressBar

    indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN
    )
}

fun ViewManager.textTitle(score: String?) = textView {
    padding = dip(16)
    textSize = 48f
    setTypeface(null, Typeface.BOLD)
    text = score
}

fun ViewManager.textSubTitle(subTitle: String?) = textView {
    topPadding = dip(8)
    gravity = Gravity.CENTER
    textSize = 18f
    setTypeface(null, Typeface.BOLD)
    text = subTitle
}

fun ViewManager.textCenter(title: String?) = textView {
    gravity = Gravity.CENTER
    leftPadding = dip(8)
    rightPadding = dip(8)
    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
    text = title
}

fun ViewManager.layoutDetailItem(title: String?, leftContent: String?, rightContent: String?) = linearLayout {
    topPadding = dip(8)

    textView {
        rightPadding = dip(8)
        text = leftContent
    }.lparams(matchParent, wrapContent, 1f)

    textCenter(title)

    textView {
        gravity = Gravity.RIGHT
        leftPadding = dip(8)
        text = rightContent
    }.lparams(matchParent, wrapContent, 1f)
}

fun ViewManager.layoutTeamBadge(idTeamBadge: Int, teamName: String?, teamFormation: String?) = linearLayout {
    orientation = LinearLayout.VERTICAL

    imageView {
        id = idTeamBadge
    }.lparams {
        width = dip(100)
        height = dip(100)
        gravity = Gravity.CENTER
    }

    textView {
        gravity = Gravity.CENTER
        textColor = ContextCompat.getColor(context, R.color.colorPrimary)
        textSize = 24f
        setTypeface(null, Typeface.BOLD)
        text = teamName
    }

    textView {
        gravity = Gravity.CENTER
        text = teamFormation
    }
}
