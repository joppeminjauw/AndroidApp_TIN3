package com.example.calesports.ui.dota

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calesports.database.CryptonicDatabaseDao
import com.example.calesports.database.entity.Match
import com.example.calesports.helpers.CryptonicApiStatus
import kotlinx.coroutines.*

class DotaViewModel(val database: CryptonicDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<CryptonicApiStatus>()
    val status: LiveData<CryptonicApiStatus>
        get() = _status

    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>>
        get() = _matches

    private var getMatchesJob = Job()
    private val coroutineScope = CoroutineScope(getMatchesJob + Dispatchers.Main)

    init {
        getMatches()
    }

    override fun onCleared() {
        super.onCleared()
        getMatchesJob.cancel()
    }

    private fun getMatches() {
        coroutineScope.launch {
            try {
                _status.value = CryptonicApiStatus.LOADING
                val listResult = getMatchesFromDb()
                _status.value = CryptonicApiStatus.DONE
                _matches.value = listResult!!.filter { Match -> Match.videogame!!.id === 4 }
            } catch (e: Exception) {
                _status.value = CryptonicApiStatus.ERROR
                _matches.value = emptyList()
            }
        }
    }

    private suspend fun getMatchesFromDb(): List<Match>? {
        return withContext(Dispatchers.IO) {
            database.getAllMatches()
        }
    }
}
