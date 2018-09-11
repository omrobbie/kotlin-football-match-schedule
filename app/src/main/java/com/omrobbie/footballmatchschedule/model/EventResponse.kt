package com.omrobbie.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

class EventResponse {

    @SerializedName("events")
    var events: List<EventsItem>? = null
}
