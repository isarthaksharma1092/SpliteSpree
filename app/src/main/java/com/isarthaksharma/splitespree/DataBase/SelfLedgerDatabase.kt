package com.isarthaksharma.splitespree.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SelfLedgerDataClass::class], version = 1)
abstract class SelfLedgerDatabase: RoomDatabase() {
    companion object{
        const val NAME = "SelfLedgerDB"
    }

    abstract fun getAllExpenseData(): SelfLedgerDAO
}