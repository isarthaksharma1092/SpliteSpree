package com.isarthaksharma.splitespree.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonalDataClass(
    val ExpenseName:String,
    val ExpenseAmt:String,
    val ExpenseMsg:String,
    val ExpenseDate:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)