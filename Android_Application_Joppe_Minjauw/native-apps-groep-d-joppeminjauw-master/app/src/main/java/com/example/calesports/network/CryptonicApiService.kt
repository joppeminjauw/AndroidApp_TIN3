package com.example.calesports.network

import com.example.calesports.database.entity.Match
import com.example.calesports.database.entity.Team
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private const val BASE_URL = "https://api.pandascore.co/"
private const val AUTH_TOKEN = "UdRbzDQP31YPUq0Cf8CiwU7sGq4Ho1Hn4AR0Hs9lO0Ge9O252W8"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CryptonicApiService {

    @GET("csgo/matches/past?token=$AUTH_TOKEN")
    fun getCsgoPastGames():
            Call<List<Match>>

    @GET("lol/matches/past?token=$AUTH_TOKEN")
    fun getLolPastGames():
            Call<List<Match>>

    @GET("dota2/matches/past?token=$AUTH_TOKEN")
    fun getDotaPastGames():
            Call<List<Match>>

    @GET("teams/{id}?token=$AUTH_TOKEN")
    fun getTeamAndPlayers(@Path("id")id: Int):
            Call<Team>
}

object CryptonicApi {
    val retrofitService: CryptonicApiService by lazy {
        retrofit.create(CryptonicApiService::class.java)
    }
}