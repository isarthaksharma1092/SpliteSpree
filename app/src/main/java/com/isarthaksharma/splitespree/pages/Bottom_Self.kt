package com.isarthaksharma.splitespree.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bottom_Self() {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground),

        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Content
            Text(
                text = "Personal Expense",
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.doto, FontWeight.ExtraBold)),
                color = MaterialTheme.colorScheme.background
            )


            LazyRow {

            }

        }

        // Floating Action Button
        FloatingActionButton(
            modifier = Modifier.padding(15.dp),
            onClick = {
                isSheetOpen = true
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Expense"
            )
        }
        if(isSheetOpen){
            AddExpense(sheetState, onDismiss = {isSheetOpen = false})
        }
    }
}

