package com.omrobbie.footballmatchschedule.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.omrobbie.footballmatchschedule.model.EventsItem
import org.jetbrains.anko.db.*

class FavoritesDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorites.db", null, 1) {

    companion object {
        private var instance: FavoritesDbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): FavoritesDbOpenHelper {
            if (instance == null) {
                instance = FavoritesDbOpenHelper(context.applicationContext)
            }

            return instance as FavoritesDbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(EventsItem.TABLE_FAVORITES, true,
                EventsItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventsItem.ID_EVENT to TEXT,
                EventsItem.DATE to TEXT,

                // home team
                EventsItem.HOME_ID to TEXT,
                EventsItem.HOME_TEAM to TEXT,
                EventsItem.HOME_SCORE to TEXT,
                EventsItem.HOME_FORMATION to TEXT,
                EventsItem.HOME_GOAL_DETAILS to TEXT,
                EventsItem.HOME_SHOTS to TEXT,
                EventsItem.HOME_LINEUP_GOALKEEPER to TEXT,
                EventsItem.HOME_LINEUP_DEFENSE to TEXT,
                EventsItem.HOME_LINEUP_MIDFIELD to TEXT,
                EventsItem.HOME_LINEUP_FORWARD to TEXT,
                EventsItem.HOME_LINEUP_SUBSTITUTES to TEXT,

                // away team
                EventsItem.AWAY_ID to TEXT,
                EventsItem.AWAY_TEAM to TEXT,
                EventsItem.AWAY_SCORE to TEXT,
                EventsItem.AWAY_FORMATION to TEXT,
                EventsItem.AWAY_GOAL_DETAILS to TEXT,
                EventsItem.AWAY_SHOTS to TEXT,
                EventsItem.AWAY_LINEUP_GOALKEEPER to TEXT,
                EventsItem.AWAY_LINEUP_DEFENSE to TEXT,
                EventsItem.AWAY_LINEUP_MIDFIELD to TEXT,
                EventsItem.AWAY_LINEUP_FORWARD to TEXT,
                EventsItem.AWAY_LINEUP_SUBSTITUTES to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventsItem.TABLE_FAVORITES, true)
    }
}

val Context.database: FavoritesDbOpenHelper
    get() = FavoritesDbOpenHelper.getInstance(applicationContext)
