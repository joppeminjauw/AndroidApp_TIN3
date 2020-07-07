package com.example.calesports.helpers

import android.util.Log
import com.example.calesports.database.CryptonicDatabaseDao
import com.example.calesports.database.entity.Match
import com.example.calesports.network.CryptonicApi
import kotlinx.coroutines.*
import retrofit2.await

class InflateDatabase(val database: CryptonicDatabaseDao) {
    private var getMatchesJob = Job()
    private val coroutineScope = CoroutineScope(getMatchesJob + Dispatchers.Main)

    fun inflateDb() {
        coroutineScope.launch {
            var getCsgoMatchesDeferred = CryptonicApi.retrofitService.getCsgoPastGames()
            val listResult = getCsgoMatchesDeferred.await()
            insert(listResult)
            var getLolMatchesDeferred = CryptonicApi.retrofitService.getLolPastGames()
            val listResult2 = getLolMatchesDeferred.await()
            insert(listResult2)
            /*var getDotaMatchesDeferred = CryptonicApi.retrofitService.getDotaPastGames()
            val listResult3 = getDotaMatchesDeferred.await()
            insert(listResult3)*/
        }
    }

    private suspend fun insert(match: List<Match>) {
        withContext(Dispatchers.IO) {
            try {
                database.insertAll(match)
            } catch (e: Exception) {
                Log.e("dbError", e.message!!)
            }
        }
    }
}