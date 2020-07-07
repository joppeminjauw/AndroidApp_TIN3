package com.example.calesports.ui.matchInfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calesports.database.CryptonicDatabaseDao
import com.example.calesports.database.entity.Match
import com.example.calesports.database.entity.Player
import com.example.calesports.database.entity.Team
import com.example.calesports.helpers.CryptonicApiStatus
import com.example.calesports.network.CryptonicApi
import kotlinx.coroutines.*
import retrofit2.await

class MatchInfoViewModel(val match: Match, val database: CryptonicDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var getMatchesJob = Job()
    private val coroutineScope = CoroutineScope(getMatchesJob + Dispatchers.Main)

    private val _selectedMatch = MutableLiveData<Match>()
    val selectedMatch: LiveData<Match>
        get() = _selectedMatch

    private val _teamOne = MutableLiveData<Team>()
    val teamOne: LiveData<Team>
        get() = _teamOne

    private val _teamTwo = MutableLiveData<Team>()
    val teamTwo: LiveData<Team>
        get() = _teamTwo

    private val _playersOne = MutableLiveData<List<Player>>()
    val playersOne: LiveData<List<Player>>
        get() = _playersOne

    private val _playersTwo = MutableLiveData<List<Player>>()
    val playersTwo: LiveData<List<Player>>
        get() = _playersTwo

    private val _status = MutableLiveData<CryptonicApiStatus>()
    val status: LiveData<CryptonicApiStatus>
        get() = _status

    private val _testStatus = MutableLiveData<Int>()
    val testStatus: LiveData<Int>
        get() = _testStatus

    init {
        _selectedMatch.value = match
        getTeamsFromApi()
    }

    fun getTeamsFromDb() {
        coroutineScope.launch {
            try {
                _status.value = CryptonicApiStatus.LOADING
                _teamOne.value = getTeam(match.opponents!![0].opponent!!.id)
                _teamTwo.value = getTeam(match.opponents!![1].opponent!!.id)
                _status.value = CryptonicApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CryptonicApiStatus.ERROR
            }
        }
    }

    private fun getTeamsFromApi() {
        coroutineScope.launch {
            val teamOneDeferred = CryptonicApi.retrofitService.getTeamAndPlayers(match.opponents!![0].opponent!!.id)
            val teamTwoDeferred = CryptonicApi.retrofitService.getTeamAndPlayers(match.opponents!![1].opponent!!.id)
            try {
                val resultOne = teamOneDeferred.await()
                _playersOne.value = resultOne.players
                insert(resultOne)
                val resultTwo = teamTwoDeferred.await()
                _playersTwo.value = resultTwo.players
                insert(resultTwo)
                _testStatus.value = 1
            } catch (e: Exception) {
                Log.e("TeamsError", e.message)
            }
        }
    }

    private suspend fun getTeam(id: Int): Team? {
        var result = Team("", 2, "", "", emptyList())
        withContext(Dispatchers.IO) {
            try {
                result = database.getTeam(id)!!
            } catch (e: Exception) {
                Log.e("dbError", e.message!!)
            }
        }

        return result
    }

    override fun onCleared() {
        super.onCleared()
        getMatchesJob.cancel()
    }

    private suspend fun insert(team: Team) {
        withContext(Dispatchers.IO) {
            try {
                database.insertTeam(team)
            } catch (e: Exception) {
                Log.e("dbError", e.message!!)
            }
        }
    }
}
