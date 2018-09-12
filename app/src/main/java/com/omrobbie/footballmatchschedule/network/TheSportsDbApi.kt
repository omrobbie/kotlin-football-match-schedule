package com.omrobbie.footballmatchschedule.network

import com.omrobbie.footballmatchschedule.BuildConfig

object TheSportsDbApi {

    fun getLeagueAll(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/all_leagues.php"
    }

    fun getLeaguePrev(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=${id}"
    }
}
