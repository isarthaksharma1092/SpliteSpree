package com.isarthaksharma.splitespree.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.MainActivity
import com.isarthaksharma.splitespree.R
import com.isarthaksharma.splitespree.model.Authentication.SessionManager


@Composable
fun Bottom_Settings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 60.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        val context = LocalContext.current
        val sessionManager = SessionManager(context)
        Text(
            text = "Settings",
            fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp
        )

        Box {
            Column {
                Button(
                    onClick = {
                        sessionManager.logout()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as? Activity)?.finish()
                        Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(text = "Logout")

                }
            }
        }
    }
}