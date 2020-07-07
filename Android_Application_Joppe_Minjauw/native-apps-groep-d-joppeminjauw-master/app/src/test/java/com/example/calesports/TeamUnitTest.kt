package com.example.calesports

import com.example.calesports.database.entity.Team
import junit.framework.Assert.assertEquals
import org.junit.Test

class TeamUnitTest {

    @Test
    fun team_isCreated(){
        val team = Team("", 3, "", "TestTeam", emptyList())

        assertEquals("TestTeam", team.name)
    }
}