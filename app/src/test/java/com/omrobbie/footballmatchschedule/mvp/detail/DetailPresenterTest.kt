package com.omrobbie.footballmatchschedule.mvp.detail

import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.model.TeamDetailResponse
import com.omrobbie.footballmatchschedule.model.TeamsItem
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.network.TheSportsDbApi
import com.omrobbie.footballmatchschedule.utils.TestContextProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    lateinit var view: DetailView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    lateinit var presenter: DetailPresenter

    @Before
    fun setupEnv() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamDetails() {
        val data: MutableList<TeamsItem> = mutableListOf()
        val response = TeamDetailResponse(data)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportsDbApi.getTeamDetails(id)),
                TeamDetailResponse::class.java)
        ).thenReturn(response)

        presenter.getTeamDetails(id, id)

        verify(view).showLoading()
        verify(view).showTeamDetails(response.teams!!, response.teams!!)
        verify(view).hideLoading()
    }
}
