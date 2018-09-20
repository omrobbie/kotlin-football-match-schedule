package com.omrobbie.footballmatchschedule.model

data class Favorites(
        val id: Long?,
        val dateEvent: String?,
        val idHomeTeam: String?,
        val strHomeTeam: String?,
        val intHomeScore: String?,
        val idAwayTeam: String?,
        val strAwayTeam: String?,
        val intAwayScore: String?) {

    companion object {
        const val TABLE_FAVORITES = "TABLE_FAVORITES"
        const val ID = "ID"
        const val DATE = "DATE"
        const val HOME_ID = "HOME_ID"
        const val HOME_TEAM = "HOME_TEAM"
        const val HOME_SCORE = "HOME_SCORE"
        const val AWAY_ID = "AWAY_ID"
        const val AWAY_TEAM = "AWAY_TEAM"
        const val AWAY_SCORE = "AWAY_SCORE"
    }
}
