package com.isarthaksharma.splitespree.DataBase

import android.app.Application
import androidx.room.Room

class RoomDBBuilder:Application(){
    val PersonalDB = Room.databaseBuilder(
        applicationContext,
        PersonalDatabase::class.java, "Personal_DATABASE"
    ).build()
}
