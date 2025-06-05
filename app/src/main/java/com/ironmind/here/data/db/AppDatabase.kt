package com.ironmind.here.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ironmind.here.data.models.Etudiant
import com.ironmind.here.data.models.Prof
import com.ironmind.here.data.models.Seance

@Database(entities = [Prof::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profDao(): ProfDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appli_presence_1.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


