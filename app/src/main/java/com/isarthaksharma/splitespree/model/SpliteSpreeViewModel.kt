package com.isarthaksharma.splitespree.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isarthaksharma.splitespree.DataBase.RoomDBInstance
import com.isarthaksharma.splitespree.DataBase.SelfLedgerDataClass
import kotlinx.coroutines.launch
import java.util.Date

class SpliteSpreeViewModel: ViewModel() {
    val SelfLedgerDAO = RoomDBInstance.SpliteSpree.getAllExpenseData()
    val SelfLedgerList:LiveData<List<SelfLedgerDataClass>> = SelfLedgerDAO.getAllExpenseData()

    fun addExpense(
        ExpenseName:String,
        ExpenseAmount:String,
        ExpenseMessage:String,
        ExpenseCreationDate: Date
    ){
        viewModelScope.launch {
            SelfLedgerDAO.addExpense(SelfLedgerDataClass(
                Expense_Name = ExpenseName,
                Expense_Amount = ExpenseAmount,
                Expense_Message = ExpenseMessage,
//                Expense_CreationDate = ExpenseCreationDate
            ))
        }
    }
    fun removeExpense(id:Int){
        viewModelScope.launch {
            SelfLedgerDAO.removeExpense(id)
        }
    }
}