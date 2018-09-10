package com.omrobbie.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class LeaguesItem {

    @SerializedName("strLeagueAlternate")
    var strLeagueAlternate: String? = null

    @SerializedName("strLeague")
    var strLeague: String? = null

    @SerializedName("strSport")
    var strSport: String? = null

    @SerializedName("idLeague")
    var idLeague: String? = null

    override fun toString(): String {
        return strLeague.toString()
    }
}
