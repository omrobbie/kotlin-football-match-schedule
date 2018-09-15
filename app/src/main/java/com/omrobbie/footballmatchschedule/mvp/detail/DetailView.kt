package com.omrobbie.footballmatchschedule.mvp.detail

import com.omrobbie.footballmatchschedule.model.TeamsItem

interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>)
}
