package com.example.calesports.ui.matchInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calesports.database.CryptonicDatabaseDao
import com.example.calesports.database.entity.Match
import java.lang.IllegalArgumentException

class MatchInfoViewModelFactory(
    private val match: Match,
    private val application: Application,
    private val dataSource: CryptonicDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchInfoViewModel::class.java)) {
            return MatchInfoViewModel(match, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}