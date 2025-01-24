package com.isarthaksharma.splitespree.DataBase

import android.app.Application
import androidx.room.Room

class RoomDBInstance:Application() {
    companion object{
        lateinit var SpliteSpree:SelfLedgerDatabase
    }

    override fun onCreate() {
        super.onCreate()
        SpliteSpree = Room.databaseBuilder(
            applicationContext,
            SelfLedgerDatabase::class.java,
            SelfLedgerDatabase.NAME
        ).build()
    }
}