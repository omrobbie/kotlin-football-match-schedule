package com.omrobbie.footballmatchschedule.mvp.schedule

import com.omrobbie.footballmatchschedule.model.LeagueResponse

interface ScheduleView {

    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
}