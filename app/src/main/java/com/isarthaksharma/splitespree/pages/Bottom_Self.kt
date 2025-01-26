package com.isarthaksharma.splitespree.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.DataBase.PersonalDatabase
import com.isarthaksharma.splitespree.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Bottom_Self() {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val dao = PersonalDatabase.getDatabase(context).PersonalDAO()
    val expense = dao.getAllPersonalExpense().collectAsState(initial = emptyList())

    val todayExpense = dao.getPersonalTodayExpense().collectAsState(initial = 0L)
    val monthExpense = dao.getPersonalMonthExpense().collectAsState(initial = 0L)
    val allTimeExpense = dao.getPersonalAllExpense().collectAsState(initial = 0L)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            // Total Spent Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(vertical = 10.dp)
                    .background(color = Color(0xff78EE78)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Spent",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 25.sp,
                                fontFamily = FontFamily(Font(R.font.doto)),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                text = "₹ ${allTimeExpense.value}",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.doto)),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }

                    Divider(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxHeight().width(2.dp)
                    )

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Today",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.doto)),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                text = "₹ ${todayExpense.value}",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.doto)),
                            )
                            Divider(
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                            )
                            Text(
                                text = "Monthly",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.doto))

                            )
                            Text(
                                text = "₹ ${monthExpense.value}",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.doto)),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(expense.value) { expense ->
                    DisplayPersonalExpense(expense)
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.BottomEnd),
            onClick = { isSheetOpen = true })
        {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Expense"
            )
        }
        if (isSheetOpen) {
            AddExpense(sheetState, onDismiss = { isSheetOpen = false })
        }
    }
}
