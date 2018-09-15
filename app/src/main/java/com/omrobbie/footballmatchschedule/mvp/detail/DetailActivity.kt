package com.omrobbie.footballmatchschedule.mvp.detail

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.utils.DateTime
import com.omrobbie.footballmatchschedule.utils.gone
import kotlinx.android.synthetic.main.abc_alert_dialog_title_material.view.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*

const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout(intent.getParcelableExtra<EventsItem>(INTENT_DETAIL))
        setupEnv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun setupLayout(item: EventsItem) {
        scrollView {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                padding = dip(16)

                progressBar {
                    indeterminateDrawable.setColorFilter(
                            ContextCompat.getColor(ctx, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                    )
                }.lparams {
                    gravity = Gravity.CENTER
                }

                // date
                textView {
                    gravity = Gravity.CENTER
                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    text = DateTime.getLongDate(item.dateEvent!!)
                }

                // score
                linearLayout {
                    padding = dip(16)
                    gravity = Gravity.CENTER

                    textView {
                        textSize = 48f
                        setTypeface(null, Typeface.BOLD)
                        text = item.intHomeScore
                    }

                    textView {
                        padding = dip(16)
                        textSize = 24f
                        text = "vs"
                    }

                    textView {
                        textSize = 48f
                        setTypeface(null, Typeface.BOLD)
                        text = item.intAwayScore
                    }
                }

                // team
                linearLayout {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        textView {
                            gravity = Gravity.CENTER
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            textSize = 24f
                            setTypeface(null, Typeface.BOLD)
                            text = item.strHomeTeam
                        }

                        textView {
                            gravity = Gravity.CENTER
                            text = item.strHomeFormation
                        }
                    }.lparams(matchParent, wrapContent, 1f)

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        textView {
                            gravity = Gravity.CENTER
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            textSize = 24f
                            setTypeface(null, Typeface.BOLD)
                            text = item.strAwayTeam
                        }

                        textView {
                            gravity = Gravity.CENTER
                            text = item.strAwayFormation
                        }
                    }.lparams(matchParent, wrapContent, 1f)
                }

                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, dip(1)) {
                    topMargin = dip(8)
                }

                // goals
                linearLayout {
                    topPadding = dip(8)

                    textView {
                        text = item.strHomeGoalDetails
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Goals"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayGoalDetails
                    }.lparams(matchParent, wrapContent, 1f)
                }

                // shots
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.intHomeShots
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Shots"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.intAwayShots
                    }.lparams(matchParent, wrapContent, 1f)
                }

                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, dip(1)) {
                    topMargin = dip(8)
                }

                // lineups
                textView {
                    topPadding = dip(8)
                    gravity = Gravity.CENTER
                    textSize = 18f
                    setTypeface(null, Typeface.BOLD)
                    text = "Lineups"
                }

                // goal keeper
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.strHomeLineupGoalkeeper
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Goal Keeper"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayLineupGoalkeeper
                    }.lparams(matchParent, wrapContent, 1f)
                }

                // defense
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.strHomeLineupDefense
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Defense"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayLineupDefense
                    }.lparams(matchParent, wrapContent, 1f)
                }

                // midfield
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.strHomeLineupMidfield
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Midfield"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayLineupMidfield
                    }.lparams(matchParent, wrapContent, 1f)
                }

                // forward
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.strHomeLineupForward
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Forward"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayLineupForward
                    }.lparams(matchParent, wrapContent, 1f)
                }

                // substitutes
                linearLayout {
                    topPadding = dip(16)

                    textView {
                        text = item.strHomeLineupSubstitutes
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        leftPadding = dip(8)
                        rightPadding = dip(8)
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = "Substitutes"
                    }

                    textView {
                        gravity = Gravity.RIGHT
                        text = item.strAwayLineupSubstitutes
                    }.lparams(matchParent, wrapContent, 1f)
                }
            }
        }
    }

    fun setupEnv() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }
}
