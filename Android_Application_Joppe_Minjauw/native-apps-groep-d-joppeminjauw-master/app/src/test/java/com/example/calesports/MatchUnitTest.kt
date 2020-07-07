package com.example.calesports

import com.example.calesports.database.entity.Match
import junit.framework.Assert.assertEquals
import org.junit.Test

class MatchUnitTest {

    @Test
    fun match_isCreated() {
        val match = Match("", "", 5, null, 4, "", "", "", "TestMatch", 3, emptyList(), emptyList(), "", null, 2, null, null, null, 2)

        assertEquals("TestMatch", match.name)
    }
}