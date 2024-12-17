package com.example.nechitaflorinas2b.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReportScreen(onYesClick: () -> Unit, onCancelClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are you sure you want to report this joke?",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(onClick = onYesClick) { Text("YES") }
                TextButton(onClick = onCancelClick) { Text("CANCEL") }
            }
        }
    }
}
