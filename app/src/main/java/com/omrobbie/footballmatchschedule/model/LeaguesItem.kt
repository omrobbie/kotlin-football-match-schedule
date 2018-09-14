package com.omrobbie.footballmatchschedule.model

data class LeaguesItem(
        var strLeagueAlternate: String?,
        var strLeague: String?,
        var strSport: String?,
        var idLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
