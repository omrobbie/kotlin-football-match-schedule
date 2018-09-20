package com.omrobbie.footballmatchschedule.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.omrobbie.footballmatchschedule.model.Favorites
import org.jetbrains.anko.db.*

class FavoritesDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorites.db", null, 1) {

    companion object {
        var instance: FavoritesDbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): FavoritesDbOpenHelper {
            if (instance == null) {
                instance = FavoritesDbOpenHelper(context.applicationContext)
            }

            return instance as FavoritesDbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorites.TABLE_FAVORITES, true,
                Favorites.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorites.DATE to TEXT,
                Favorites.HOME_ID to TEXT,
                Favorites.HOME_TEAM to TEXT,
                Favorites.HOME_SCORE to TEXT,
                Favorites.AWAY_ID to TEXT,
                Favorites.AWAY_TEAM to TEXT,
                Favorites.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorites.TABLE_FAVORITES, true)
    }
}

val Context.database: FavoritesDbOpenHelper
    get() = FavoritesDbOpenHelper.getInstance(applicationContext)
