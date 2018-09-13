package com.omrobbie.footballmatchschedule.mvp.match

import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.LeagueResponse

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventListPrev(data: List<EventsItem>)
    fun showEventListNext(data: List<EventsItem>)
}