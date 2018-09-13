package com.omrobbie.footballmatchschedule.mvp.match

import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.model.EventResponse
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(val view: MatchView) {

    val apiRepository = ApiRepository()
    val gson = Gson()

    var match = 1

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }

    fun getEventsPrev(id: String) {
        match = 1
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeaguePrev(id)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventListPrev(data.events!!)
                } catch (e: NullPointerException) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getEventsNext(id: String) {
        match = 2
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeagueNext(id)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventListPrev(data.events!!)
                } catch (e: NullPointerException) {
                    view.showEmptyData()
                }
            }
        }
    }
}
