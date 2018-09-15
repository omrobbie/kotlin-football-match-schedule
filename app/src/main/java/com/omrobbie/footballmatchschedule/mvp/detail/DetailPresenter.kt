package com.omrobbie.footballmatchschedule.mvp.detail

import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.model.TeamDetailResponse
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(val view: DetailView) {

    val apiRepository = ApiRepository()
    val gson = Gson()

    fun getTeamDetails(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()

        doAsync {
            val dataHomeTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamDetails(idHomeTeam)),
                    TeamDetailResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamDetails(idAwayTeam)),
                    TeamDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetails(dataHomeTeam.teams!!, dataAwayTeam.teams!!)
            }
        }
    }
}
