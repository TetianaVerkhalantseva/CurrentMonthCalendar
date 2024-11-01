package com.example.oblig1

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun countAmountOfWorkingDays() {
        val daysArray = arrayOf(
            null, 1, 2, 3, 4, 5, 6, 7,
            null, 8, 9, 10, 11, 12, 13, 14,
            null, 15, 16, 17, 18, 19, 20, 21,
            null, 22, 23, 24, 25, 26, 27, 28,
            null, 29, 30, 31, null, null, null, null
        )
        assertEquals(23, countWorkingDaysAmount(daysArray, 31))
    }

    @Test
    fun countAmountOfDaysSinceStartOfYear1() {
        assertEquals(10, com.example.oblig1.countAmountOfDaysSinceStartOfYear(1, 10))
    }

    @Test
    fun countAmountOfDaysSinceStartOfYear2() {
        assertEquals(9, com.example.oblig1.countAmountOfDaysSinceStartOfYear(1, 9))
    }

    @Test
    fun countAmountOfDaysSinceStartOfYear3() {
        assertEquals(290, com.example.oblig1.countAmountOfDaysSinceStartOfYear(10, 17))
    }
}