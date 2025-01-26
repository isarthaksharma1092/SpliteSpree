package com.isarthaksharma.splitespree.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.R

@Composable
fun Bottom_Groups() {
    val context = LocalContext.current
    Text(
        text = "Group",
        fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 32.sp,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {


        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { Toast.makeText(context, "Create Group", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(15.dp)
                .align(Alignment.BottomEnd)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 7.dp)
            ) {
                Text(
                    text = "Create Group",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 3.dp)
                )
                Icon(
                    Icons.Filled.GroupAdd,
                    contentDescription = "Create Group",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 3.dp)
                )
            }
        }
    }
}