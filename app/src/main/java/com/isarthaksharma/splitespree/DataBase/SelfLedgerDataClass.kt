package com.isarthaksharma.splitespree.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SelfLedgerDataClass(
    @PrimaryKey(autoGenerate = true)
    val Expense_Id:Int = 0,
    val Expense_Name:String,
    val Expense_Amount:String,
    val Expense_Message:String,
//    val Expense_CreationDate: Date
)