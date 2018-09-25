package com.omrobbie.footballmatchschedule.mvp.detail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.helper.database
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.TeamDetailResponse
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import com.omrobbie.footballmatchschedule.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailPresenter(val view: DetailView, val apiRepository: ApiRepository, val gson: Gson, val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetails(idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()

        async(context.main) {
            val dataHomeTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDbApi.getTeamDetails(idHomeTeam.toString())),
                        TeamDetailResponse::class.java
                )
            }

            val dataAwayTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDbApi.getTeamDetails(idAwayTeam.toString())),
                        TeamDetailResponse::class.java
                )
            }

            view.hideLoading()
            view.showTeamDetails(dataHomeTeam.await().teams!!, dataAwayTeam.await().teams!!)
        }
    }

    fun addFavorites(context: Context, data: EventsItem) {
        try {
            context.database.use {
                insert(EventsItem.TABLE_FAVORITES,
                        EventsItem.ID_EVENT to data.idEvent,
                        EventsItem.DATE to data.dateEvent,

                        // home team
                        EventsItem.HOME_ID to data.idHomeTeam,
                        EventsItem.HOME_TEAM to data.strHomeTeam,
                        EventsItem.HOME_SCORE to data.intHomeScore,
                        EventsItem.HOME_FORMATION to data.strHomeFormation,
                        EventsItem.HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                        EventsItem.HOME_SHOTS to data.intHomeShots,
                        EventsItem.HOME_LINEUP_GOALKEEPER to data.strHomeLineupGoalkeeper,
                        EventsItem.HOME_LINEUP_DEFENSE to data.strHomeLineupDefense,
                        EventsItem.HOME_LINEUP_MIDFIELD to data.strHomeLineupMidfield,
                        EventsItem.HOME_LINEUP_FORWARD to data.strHomeLineupForward,
                        EventsItem.HOME_LINEUP_SUBSTITUTES to data.strHomeLineupSubstitutes,

                        // away team
                        EventsItem.AWAY_ID to data.idAwayTeam,
                        EventsItem.AWAY_TEAM to data.strAwayTeam,
                        EventsItem.AWAY_SCORE to data.intAwayScore,
                        EventsItem.AWAY_FORMATION to data.strAwayFormation,
                        EventsItem.AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                        EventsItem.AWAY_SHOTS to data.intAwayShots,
                        EventsItem.AWAY_LINEUP_GOALKEEPER to data.strAwayLineupGoalkeeper,
                        EventsItem.AWAY_LINEUP_DEFENSE to data.strAwayLineupDefense,
                        EventsItem.AWAY_LINEUP_MIDFIELD to data.strAwayLineupMidfield,
                        EventsItem.AWAY_LINEUP_FORWARD to data.strAwayLineupForward,
                        EventsItem.AWAY_LINEUP_SUBSTITUTES to data.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorites(context: Context, data: EventsItem) {
        try {
            context.database.use {
                delete(EventsItem.TABLE_FAVORITES,
                        EventsItem.ID_EVENT + " = {id}",
                        "id" to data.idEvent.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: EventsItem): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(EventsItem.TABLE_FAVORITES)
                    .whereArgs(EventsItem.ID_EVENT + " = {id}",
                            "id" to data.idEvent.toString())
                    .parseList(classParser<EventsItem>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}
