package com.omrobbie.footballmatchschedule.mvp.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.R.id.mn_favorites
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.TeamsItem
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.utils.*
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var presenter: DetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var dataView: ScrollView

    private lateinit var data: EventsItem

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

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
        progressBar.visible()
        dataView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        dataView.visible()
    }

    override fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>) {
        val imgHomeBadge = find<ImageView>(R.id.home_badge)
        Picasso.get()
                .load(dataHomeTeam[0].strTeamBadge)
                .into(imgHomeBadge)

        val imgAwayBadge = find<ImageView>(R.id.away_badge)
        Picasso.get()
                .load(dataAwayTeam[0].strTeamBadge)
                .into(imgAwayBadge)
    }

    private fun setupLayout(data: EventsItem) {
        relativeLayout {
            dataView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textCenter(DateTime.getLongDate(data.dateEvent))

                    // score
                    linearLayout {
                        gravity = Gravity.CENTER

                        textTitle(data.intHomeScore)
                        textTitle("vs")
                        textTitle(data.intAwayScore)
                    }

                    // team
                    linearLayout {
                        layoutTeamBadge(R.id.home_badge, data.strHomeTeam, data.strHomeFormation)
                                .lparams(matchParent, wrapContent, 1f)

                        layoutTeamBadge(R.id.away_badge, data.strAwayTeam, data.strAwayFormation)
                                .lparams(matchParent, wrapContent, 1f)
                    }

                    line()

                    layoutDetailItem("Goals", data.strHomeGoalDetails, data.strAwayGoalDetails)
                    layoutDetailItem("Shots", data.intHomeShots, data.intAwayShots)

                    line()

                    // lineups
                    textSubTitle("Lineups")

                    layoutDetailItem("Goal Keeper", data.strHomeLineupGoalkeeper, data.strAwayLineupGoalkeeper)
                    layoutDetailItem("Defense", data.strHomeLineupDefense, data.strAwayLineupDefense)
                    layoutDetailItem("Midfield", data.strHomeLineupMidfield, data.strAwayLineupMidfield)
                    layoutDetailItem("Forward", data.strHomeLineupForward, data.strAwayLineupForward)
                    layoutDetailItem("Substitutes", data.strHomeLineupSubstitutes, data.strAwayLineupSubstitutes)
                }
            }

            progressBar(R.id.progress_bar).lparams {
                centerInParent()
            }
        }
    }

    private fun setupEnv(data: EventsItem) {
        progressBar = find(R.id.progress_bar)

        presenter = DetailPresenter(this, ApiRepository(), Gson())
        presenter.getTeamDetails(data.idHomeTeam, data.idAwayTeam)

        isFavorite = presenter.isFavorite(ctx, data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_yes)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites_no)
        }
    }
}
