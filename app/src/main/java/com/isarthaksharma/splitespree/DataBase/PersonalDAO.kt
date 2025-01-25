package com.isarthaksharma.splitespree.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDAO {
    @Query("Select * from PersonalDataClass")
    fun getAllPersonalExpense(): Flow<List<PersonalDataClass>>

    @Insert
    suspend fun addPersonalExpense(expense: PersonalDataClass)

    @Delete
    suspend fun removePersonalExpense(expense: PersonalDataClass)
}