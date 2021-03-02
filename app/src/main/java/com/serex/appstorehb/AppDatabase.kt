package com.serex.appstorehb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "dbstore"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            return INSTANCE!!

        }
    }

}