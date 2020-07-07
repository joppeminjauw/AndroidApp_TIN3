package com.example.calesports.database

import androidx.room.*
import com.example.calesports.database.entity.Match
import com.example.calesports.database.entity.Team

@Dao
interface CryptonicDatabaseDao {

    @Insert
    fun insert(match: Match)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Match>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team)

    @Update
    fun updateMatch(match: Match)

    @Query("SELECT * FROM match_table WHERE id = :key")
    fun get(key: Int): Match?

    @Query("DELETE FROM match_table")
    fun clear()

    @Query("SELECT * FROM match_table")
    fun getAllMatches(): List<Match>

    @Query("SELECT * FROM team_table WHERE id = :key")
    fun getTeam(key: Int): Team?
}