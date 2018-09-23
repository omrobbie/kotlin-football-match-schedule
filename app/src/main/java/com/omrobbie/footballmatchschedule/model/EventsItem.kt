package com.omrobbie.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventsItem(
        val id: Long?,
        val idEvent: String?,
        val dateEvent: String?,

        // home team
        val idHomeTeam: String?,
        val strHomeTeam: String?,
        val intHomeScore: String?,
        val strHomeFormation: String?,
        val strHomeGoalDetails: String?,
        val intHomeShots: String?,
        val strHomeLineupGoalkeeper: String?,
        val strHomeLineupDefense: String?,
        val strHomeLineupMidfield: String?,
        val strHomeLineupForward: String?,
        val strHomeLineupSubstitutes: String?,

        // away team
        val idAwayTeam: String?,
        val strAwayTeam: String?,
        val intAwayScore: String?,
        val strAwayFormation: String?,
        val strAwayGoalDetails: String?,
        val intAwayShots: String?,
        val strAwayLineupGoalkeeper: String?,
        val strAwayLineupDefense: String?,
        val strAwayLineupMidfield: String?,
        val strAwayLineupForward: String?,
        val strAwayLineupSubstitutes: String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITES = "TABLE_FAVORITES"
        const val ID = "ID"
        const val ID_EVENT = "ID_EVENT"
        const val DATE = "DATE"

        // home team
        const val HOME_ID = "HOME_ID"
        const val HOME_TEAM = "HOME_TEAM"
        const val HOME_SCORE = "HOME_SCORE"
        const val HOME_FORMATION = "HOME_FORMATION"
        const val HOME_GOAL_DETAILS = "HOME_GOAL_DETAILS"
        const val HOME_SHOTS = "HOME_SHOTS"
        const val HOME_LINEUP_GOALKEEPER = "HOME_LINEUP_GOALKEEPER"
        const val HOME_LINEUP_DEFENSE = "HOME_LINEUP_DEFENSE"
        const val HOME_LINEUP_MIDFIELD = "HOME_LINEUP_MIDFIELD"
        const val HOME_LINEUP_FORWARD = "HOME_LINEUP_FORWARD"
        const val HOME_LINEUP_SUBSTITUTES = "HOME_LINEUP_SUBSTITUTES"

        // away team
        const val AWAY_ID = "AWAY_ID"
        const val AWAY_TEAM = "AWAY_TEAM"
        const val AWAY_SCORE = "AWAY_SCORE"
        const val AWAY_FORMATION = "AWAY_FORMATION"
        const val AWAY_GOAL_DETAILS = "AWAY_GOAL_DETAILS"
        const val AWAY_SHOTS = "AWAY_SHOTS"
        const val AWAY_LINEUP_GOALKEEPER = "AWAY_LINEUP_GOALKEEPER"
        const val AWAY_LINEUP_DEFENSE = "AWAY_LINEUP_DEFENSE"
        const val AWAY_LINEUP_MIDFIELD = "AWAY_LINEUP_MIDFIELD"
        const val AWAY_LINEUP_FORWARD = "AWAY_LINEUP_FORWARD"
        const val AWAY_LINEUP_SUBSTITUTES = "AWAY_LINEUP_SUBSTITUTES"
    }
}
