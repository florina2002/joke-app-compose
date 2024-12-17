package com.example.nechitaflorinas2b.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nechitaflorinas2b.data.Joke
import com.example.nechitaflorinas2b.viewmodel.JokeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeApp() {
    val viewModel: JokeViewModel = viewModel()
    val jokes by viewModel.jokes.collectAsState()
    val reportedJokes by viewModel.reportedJokes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedCategory by remember { mutableStateOf("Any") }
    var showDelivery by remember { mutableStateOf<String?>(null) }
    var reportJoke by remember { mutableStateOf<Joke?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchJokesWithLoading(selectedCategory)
    }

    if (reportJoke == null) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Joke App") }, actions = {
                DropdownMenuCategory(currentCategory = selectedCategory,
                    onCategorySelected = { newCategory ->
                        selectedCategory = newCategory
                        viewModel.fetchJokesWithLoading(newCategory)
                    })
            })
        }) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                if (isLoading) {
                    LoadingScreen()
                } else {
                    JokeList(jokes = jokes, reportedJokes = reportedJokes, onJokeClick = { joke ->
                        if (joke.type == "single") {
                            Toast.makeText(
                                context, "This joke is of type SINGLE", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            showDelivery = joke.delivery
                        }
                    }, onReportClick = { joke -> reportJoke = joke })
                }

                if (showDelivery != null) {
                    AlertDialog(onDismissRequest = { showDelivery = null },
                        title = { Text(text = "Delivery") },
                        text = { Text(text = showDelivery!!) },
                        confirmButton = {
                            TextButton(onClick = { showDelivery = null }) {
                                Text("OK")
                            }
                        })
                }
            }
        }
    } else {
        ReportScreen(onYesClick = {
            viewModel.reportJoke(reportJoke!!)
            reportJoke = null
        }, onCancelClick = { reportJoke = null })
    }
}


@Composable
fun DropdownMenuCategory(
    currentCategory: String, onCategorySelected: (String) -> Unit
) {
    val categories =
        listOf("Any", "Christmas", "Programming", "Miscellaneous", "Dark", "Pun", "Spooky")
    val otherCategories = categories.filter { it != currentCategory }
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = currentCategory,
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = true })
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            otherCategories.forEach { category ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onCategorySelected(category)
                }, text = {
                    Text(text = category)
                })
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun JokeList(
    jokes: List<Joke>,
    reportedJokes: Set<Joke>,
    onJokeClick: (Joke) -> Unit,
    onReportClick: (Joke) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jokes) { joke ->
            JokeItem(joke = joke,
                isReported = reportedJokes.contains(joke),
                onClick = { onJokeClick(joke) },
                onReportClick = { onReportClick(joke) })
        }
    }
}

@Composable
fun JokeItem(joke: Joke, isReported: Boolean, onClick: () -> Unit, onReportClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val textColor = if (isReported) Color(0xffff0000)
    else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Category: ${joke.category}",
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Type: ${joke.type}",
                    color = textColor,
                    style = MaterialTheme.typography.bodySmall
                )
                if (joke.type == "single") {
                    Text(
                        text = "Joke: ${joke.joke}",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        text = "Setup: ${joke.setup}",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                joke.flags.forEach { (flag, value) ->
                    Text(
                        text = "$flag: $value",
                        color = textColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onReportClick()
                    }, text = { Text("Report record") })
                }
            }
        }
    }
}
