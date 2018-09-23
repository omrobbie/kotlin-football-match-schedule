package com.omrobbie.footballmatchschedule.utils

import org.junit.Test

import org.junit.Assert.*

class DateTimeTest {

    @Test
    fun getShortDate() {
        assertEquals("24 September 2018", DateTime.getShortDate("2018-09-24"))
    }

    @Test
    fun getLongDate() {
        assertEquals("Mon, 24 Sep 2018", DateTime.getLongDate("2018-09-24"))
    }
}
