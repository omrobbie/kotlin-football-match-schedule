package com.omrobbie.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class LeagueResponse {

    @SerializedName("leagues")
    var leagues: List<LeaguesItem>? = null
}