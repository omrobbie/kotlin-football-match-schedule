package com.omrobbie.footballmatchschedule.mvp.match

import com.omrobbie.footballmatchschedule.model.LeagueResponse

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
}