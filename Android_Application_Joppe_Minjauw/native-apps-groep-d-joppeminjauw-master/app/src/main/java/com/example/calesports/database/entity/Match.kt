package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "match_table")
@TypeConverters(Converters::class)
data class Match(
    @SerializedName("begin_at")
    val beginAt: String?,
    @SerializedName("end_at")
    val endAt: String?,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("league")
    val league: League?,
    @SerializedName("league_id")
    val leagueId: Int?,
    @SerializedName("live_url")
    val liveUrl: String?,
    @SerializedName("match_type")
    val matchType: String?,
    @SerializedName("modified_at")
    val modifiedAt: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("number_of_games")
    val numberOfGames: Int?,
    @SerializedName("opponents")
    val opponents: List<Opponent>?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("scheduled_at")
    val scheduledAt: String?,
    @SerializedName("serie")
    val serie: Serie?,
    @SerializedName("serie_id")
    val serieId: Int?,
    @SerializedName("videogame")
    val videogame: Videogame?,
    @SerializedName("tournament")
    val tournament: Tournament?,
    @SerializedName("winner")
    val winner: Opponent?,
    @SerializedName("winner_id")
    val winnerId: Int?
) : Serializable

class Converters {
    private val videogameType = object : TypeToken<Videogame>() {}.type
    private val leagueType = object : TypeToken<League>() {}.type
    private val resultType = object : TypeToken<List<Result>>() {}.type
    private val serieType = object : TypeToken<Serie>() {}.type
    private val teamType = object : TypeToken<Opponent>() {}.type
    private val tournamentType = object : TypeToken<Tournament>() {}.type
    private val teamListType = object : TypeToken<List<Opponent>>() {}.type
    private val mapType = object : TypeToken<Map>() {}.type
    private val roundScoreType = object : TypeToken<List<RoundScore>>() {}.type
    private val playerType = object : TypeToken<List<Player>>() {}.type

    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        val hulp = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        val list = hulp.toList()

        return list
    }

    @TypeConverter
    fun leagueToJson(value: League): String {
        return Gson().toJson(value, leagueType)
    }

    @TypeConverter
    fun jsonToLeague(value: String): League {
        return Gson().fromJson(value, leagueType)
    }

    @TypeConverter
    fun resultToJson(value: List<Result>): String {
        return Gson().toJson(value, resultType)
    }

    @TypeConverter
    fun jsonToResult(value: String): List<Result> {
        return Gson().fromJson(value, resultType)
    }

    @TypeConverter
    fun serieToJson(value: Serie): String {
        return Gson().toJson(value, serieType)
    }

    @TypeConverter
    fun jsonToSerie(value: String): Serie {
        return Gson().fromJson(value, serieType)
    }

    @TypeConverter
    fun teamToJson(value: Opponent): String {
        return Gson().toJson(value, teamType)
    }

    @TypeConverter
    fun jsonToTeam(value: String): Opponent {
        return Gson().fromJson(value, teamType)
    }

    @TypeConverter
    fun teamListToJson(value: List<Opponent>): String {
        return Gson().toJson(value, teamListType)
    }

    @TypeConverter
    fun jsonToTeamList(value: String): List<Opponent> {
        return Gson().fromJson(value, teamListType)
    }

    @TypeConverter
    fun playerListToJson(value: List<Player>): String {
        return Gson().toJson(value, playerType)
    }

    @TypeConverter
    fun jsonToPlayerlist(value: String): List<Player> {
        return Gson().fromJson(value, playerType)
    }

    @TypeConverter
    fun roundScoreToJson(value: List<RoundScore>): String {
        return Gson().toJson(value, roundScoreType)
    }

    @TypeConverter
    fun jsonToRoundScore(value: String): List<RoundScore> {
        return Gson().fromJson(value, roundScoreType)
    }

    @TypeConverter
    fun videoGameToJson(value: Videogame): String {
        return Gson().toJson(value, videogameType)
    }

    @TypeConverter
    fun jsonToVideogame(value: String): Videogame {
        return Gson().fromJson(value, videogameType)
    }

    @TypeConverter
    fun mapToJson(value: Map): String {
        return Gson().toJson(value, mapType)
    }

    @TypeConverter
    fun jsonToMap(value: String): Map {
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun tournamentToJson(value: Tournament): String {
        return Gson().toJson(value, tournamentType)
    }

    @TypeConverter
    fun jsonToTournament(value: String): Tournament {
        return Gson().fromJson(value, tournamentType)
    }
}