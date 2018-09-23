package com.omrobbie.footballmatchschedule.mvp.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.R.id.mn_favorites
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.TeamsItem
import com.omrobbie.footballmatchschedule.utils.DateTime
import com.omrobbie.footballmatchschedule.utils.invisible
import com.omrobbie.footballmatchschedule.utils.visible
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity(), DetailView {

    lateinit var presenter: DetailPresenter

    lateinit var progressView: ProgressBar
    lateinit var dataView: ScrollView

    lateinit var imgHomeBadge: ImageView
    lateinit var imgAwayBadge: ImageView

    lateinit var data: EventsItem

    var menuFavorites: Menu? = null
    var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra(INTENT_DETAIL)

        setupLayout(data)
        setupEnv(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            mn_favorites -> {
                if (isFavorite) {
                    presenter.removeFavorites(ctx, data)
                } else {
                    presenter.addFavorites(ctx, data)
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressView.visible()
        dataView.invisible()
    }

    override fun hideLoading() {
        progressView.invisible()
        dataView.visible()
    }

    override fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>) {
        Picasso.get()
                .load(dataHomeTeam[0].strTeamBadge)
                .into(imgHomeBadge)

        Picasso.get()
                .load(dataAwayTeam[0].strTeamBadge)
                .into(imgAwayBadge)
    }

    fun setupLayout(data: EventsItem) {
        relativeLayout {
            dataView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textView {
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = DateTime.getLongDate(data.dateEvent!!)
                    }

                    // score
                    linearLayout {
                        padding = dip(16)
                        gravity = Gravity.CENTER

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = data.intHomeScore
                        }

                        textView {
                            padding = dip(16)
                            textSize = 24f
                            text = "vs"
                        }

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = data.intAwayScore
                        }
                    }

                    // team
                    linearLayout {
                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgHomeBadge = imageView {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = data.strHomeTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = data.strHomeFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgAwayBadge = imageView {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = data.strAwayTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = data.strAwayFormation
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
                            text = data.strHomeGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goals"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // shots
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = data.intHomeShots
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Shots"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.intAwayShots
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
                            text = data.strHomeLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Goal Keeper"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // defense
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = data.strHomeLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Defense"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // midfield
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = data.strHomeLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Midfield"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // forward
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = data.strHomeLineupForward
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Forward"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayLineupForward
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    // substitutes
                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = data.strHomeLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = "Substitutes"
                        }

                        textView {
                            gravity = Gravity.RIGHT
                            text = data.strAwayLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }
            }

            progressView = progressBar {
                indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(ctx, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                )
            }.lparams {
                centerInParent()
            }
        }
    }

    fun setupEnv(data: EventsItem) {
        presenter = DetailPresenter(this)
        presenter.getTeamDetails(data.idHomeTeam!!, data.idAwayTeam!!)

        isFavorite = presenter.isFavorite(ctx, data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }

    fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_yes)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_no)
        }
    }
}
