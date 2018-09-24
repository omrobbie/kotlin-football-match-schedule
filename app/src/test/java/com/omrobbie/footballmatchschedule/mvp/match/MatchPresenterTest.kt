package com.omrobbie.footballmatchschedule.mvp.match

import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.model.EventResponse
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.model.LeaguesItem
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import com.omrobbie.footballmatchschedule.utils.TestContextProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    lateinit var view: MatchView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    lateinit var presenter: MatchPresenter

    @Before
    fun setupEnv() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueAll() {
        val data: MutableList<LeaguesItem> = mutableListOf()
        val response = LeagueResponse(data)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getLeagueAll()),
                LeagueResponse::class.java)
        ).thenReturn(response)

        presenter.getLeagueAll()

        verify(view).showLoading()
        verify(view).showLeagueList(response)
        verify(view).hideLoading()
    }

    @Test
    fun getEventsPrev() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(data)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getLeaguePrev(id)),
                EventResponse::class.java)
        ).thenReturn(response)

        presenter.getEventsPrev(id)

        verify(view).showLoading()
        verify(view).showEventList(data)
        verify(view).hideLoading()
    }

    @Test
    fun getEventsNext() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(data)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getLeagueNext(id)),
                EventResponse::class.java)
        ).thenReturn(response)

        presenter.getEventsNext(id)

        verify(view).showLoading()
        verify(view).showEventList(data)
        verify(view).hideLoading()
    }
}
