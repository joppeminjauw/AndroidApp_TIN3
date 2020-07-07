package com.example.calesports.ui.csgo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calesports.database.CryptonicDatabaseDao
import java.lang.IllegalArgumentException

class CsgoViewmodelFactory(
    private val dataSource: CryptonicDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CsgoViewModel::class.java)) {
            return CsgoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}