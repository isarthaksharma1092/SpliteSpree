package com.isarthaksharma.splitespree.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.DataBase.PersonalDataClass

@Composable
fun DisplayPersonalExpense(expense: PersonalDataClass) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {

                alpha = 0.1f // Adjust transparency
            }
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(10.dp)


    ) {
        Box{
            Column {
                Row {
                    Text(
                        text = expense.ExpenseName,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 25.sp,
                        modifier = Modifier.weight(.1f),
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = expense.ExpenseDate,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Text(
                    text = "₹ ${expense.ExpenseAmt}",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                if (expense.ExpenseMsg.isNotBlank()) {
                    Text(
                        text = expense.ExpenseMsg,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}