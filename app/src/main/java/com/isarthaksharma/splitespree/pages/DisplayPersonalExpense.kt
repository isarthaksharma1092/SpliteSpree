package com.isarthaksharma.splitespree.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.DataBase.PersonalDataClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DisplayPersonalExpense(expense: PersonalDataClass) {
    val readableDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(expense.ExpenseDate))
    Card(
        elevation = CardDefaults.cardElevation(70.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(10.dp)
        ) {
            Box {
                Column {
                    Row {
                        // Expense Name
                        Text(
                            text = expense.ExpenseName,
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 25.sp,
                            modifier = Modifier.weight(.1f),
                            fontWeight = FontWeight.Bold
                        )
                        // Expense Date
                        Text(
                            text = readableDate,
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    // Expense Amount
                    Text(
                        text = "₹ ${expense.ExpenseAmt}",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    // Expense Msg(Optional)
                    if (expense.ExpenseMsg?.isNotBlank() == true) {
                        Text(
                            text = expense.ExpenseMsg,
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}