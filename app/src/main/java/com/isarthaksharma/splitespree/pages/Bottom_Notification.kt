package com.isarthaksharma.splitespree.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.R

@Composable
fun Bottom_Notification() {
    val context = LocalContext.current

    Text(
        text = "Notifications",
        fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 32.sp,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Text(
            text = "No notification as of now",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}