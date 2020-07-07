package com.example.calesports.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calesports.database.entity.Match
import com.example.calesports.database.entity.Team

@Database(entities = arrayOf(Match::class, Team::class), version = 7, exportSchema = false)
abstract class CryptonicDatabase : RoomDatabase() {

    abstract val cryptonicDatabaseDao: CryptonicDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: CryptonicDatabase? = null

        fun getInstance(context: Context): CryptonicDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CryptonicDatabase::class.java,
                        "cryptonic_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}