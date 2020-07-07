package com.example.calesports.ui.lol

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calesports.database.CryptonicDatabaseDao
import java.lang.IllegalArgumentException

class LolViewmodelFactory(
    private val dataSource: CryptonicDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LolViewModel::class.java)) {
            return LolViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}