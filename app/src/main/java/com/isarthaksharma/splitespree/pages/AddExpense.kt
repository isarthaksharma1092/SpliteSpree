package com.isarthaksharma.splitespree.pages

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.model.SpliteSpreeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(sheetState: SheetState, onDismiss: () -> Unit,viewModel: SpliteSpreeViewModel) {
    val selfExpense by viewModel.SelfLedgerList.observeAsState()
    var expenditureState by remember { mutableStateOf("") }
    var amountState by remember { mutableStateOf("") }
    var msgState by remember { mutableStateOf("") }

    // Dates and Current Date
    val context = LocalContext.current
    val mCalendar = Calendar.getInstance()

    // Today's date for the maxDate
    val today = mCalendar.timeInMillis
    val currentDate =
        "${mCalendar.get(Calendar.DAY_OF_MONTH)}/${mCalendar.get(Calendar.MONTH) + 1}/${
            mCalendar.get(Calendar.YEAR)
        }"
    val selectedDate = remember { mutableStateOf(Date()) }
    mCalendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate.value = mCalendar.time
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    )



//*************************************************************************************************************************************************************
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        //  UI
        Box( modifier = Modifier.fillMaxSize())
        {

            Column(modifier = Modifier.padding(10.dp))
            {
                // Add Expenditure
                OutlinedTextField(
                    value = expenditureState,
                    label = { Text("Expense Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    onValueChange = { expenditureState = it },
                    leadingIcon = {
                        Icon(
                            Icons.Default.ProductionQuantityLimits,
                            contentDescription = "",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )

                // Add amount and currency selector
                Row {
                    OutlinedTextField(
                        value = amountState,
                        label = { Text("Amount") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth().padding(5.dp).weight(.1f),
                        onValueChange = { amountState = it },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Money,
                                contentDescription = "",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )

                    Icon(
                        Icons.Default.CurrencyRupee,
                        contentDescription = "Currency Rupees INR",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 5.dp)
                    )
                }

                // Add Note About The Item
                OutlinedTextField(
                    value = msgState,
                    label = { Text("Any Message (Optional) ") },
                    singleLine = true,
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    onValueChange = {
                        msgState = it
                    },
                    leadingIcon = {
                        Icon(
                            Icons.AutoMirrored.Filled.Note,
                            contentDescription = "",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )

                    }
                )

                // Select Time and Date
                Button(
                    onClick = {
                        datePickerDialog.datePicker.maxDate = today
                        datePickerDialog.show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(16.dp),
                ) {
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.value)
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }


                Button(
                    onClick = {
                        // Save Into Storage and display on lazy list
                        // Invalid Expenditure Name
                        if (expenditureState.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Let’s call it ‘unplanned shopping spree’!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // Invalid Amount
                        else if (amountState.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Don't you think every product needs a price tag, or is it just 'mystery box' time?",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        // Invalid Amount
                        else if (amountState.toDoubleOrNull() == null) {
                            Toast.makeText(
                                context,
                                "Amount can't have a digit dumbo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // Action
                        else {
                            viewModel.addExpense(expenditureState,amountState,msgState,selectedDate.value)
                            Toast.makeText(context, "Expense Save", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp),

                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Add Expense")
                }
            }
        }
    }
}


