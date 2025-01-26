package com.isarthaksharma.splitespree.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonalDataClass(
    val ExpenseName:String,
    val ExpenseAmt:Double,
    val ExpenseMsg:String?,
    val ExpenseDate:Long,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)