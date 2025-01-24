package com.isarthaksharma.splitespree.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SelfLedgerDAO {
    @Query("Select* From SelfLedgerDataClass")
    fun getAllExpenseData():LiveData<List<SelfLedgerDataClass>>

    @Insert
    fun addExpense(selfLedgerDataClass: SelfLedgerDataClass)

    @Query("Delete From SelfLedgerDataClass where Expense_Id == :Expense_Id ")
    fun removeExpense(Expense_Id:Int)

}