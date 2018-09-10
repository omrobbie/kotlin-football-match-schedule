package com.omrobbie.footballmatchschedule.mvp.schedule

import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SchedulePresenter(val view: ScheduleView) {

    val apiRepository = ApiRepository()
    val gson = Gson()

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeagueAll()),
                    LeagueResponse::class.java
            )


            print(data)

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }
}