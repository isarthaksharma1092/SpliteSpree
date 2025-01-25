package com.isarthaksharma.splitespree.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PersonalDataClass::class],
    version = 1
)
abstract class PersonalDatabase : RoomDatabase() {
    abstract fun PersonalDAO(): PersonalDAO

    companion object{
        private var INSTANCE: PersonalDatabase? = null

        fun getDatabase(context: Context): PersonalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonalDatabase::class.java,
                    "Personal_DATABASE"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
