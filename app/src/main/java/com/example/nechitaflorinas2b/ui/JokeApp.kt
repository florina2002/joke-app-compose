package com.example.nechitaflorinas2b.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
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
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedCategory by remember { mutableStateOf("Any") }
    var showDelivery by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchJokes(selectedCategory)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Joke App") },
                actions = {
                    DropdownMenuCategory(
                        currentCategory = selectedCategory,
                        onCategorySelected = { newCategory ->
                            selectedCategory = newCategory
                            viewModel.fetchJokesWithLoading(newCategory)
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            if (isLoading) {
                LoadingScreen()
            } else {
                JokeList(jokes = jokes, context = context, onJokeClick = { joke ->
                    if (joke.type == "single") {
                        Toast.makeText(
                            context,
                            "This joke is of type SINGLE",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showDelivery = joke.delivery
                    }
                })
            }

            if (showDelivery != null) {
                AlertDialog(
                    onDismissRequest = { showDelivery = null },
                    title = { Text(text = "Delivery") },
                    text = { Text(text = showDelivery!!) },
                    confirmButton = {
                        TextButton(onClick = { showDelivery = null }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun DropdownMenuCategory(
    currentCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Any", "Christmas", "Programming", "Miscellaneous", "Dark", "Pun", "Spooky")
    val otherCategories = categories.filter { it != currentCategory } // Exclude the current category
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        // Display the current category as the main action
        Text(
            text = currentCategory,
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = true }
        )

        // Dropdown menu for the other categories
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            otherCategories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onCategorySelected(category)
                    },
                    text = {
                        Text(text = category)
                    }
                )
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun JokeList(jokes: List<Joke>, context: android.content.Context, onJokeClick: (Joke) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jokes) { joke ->
            JokeItem(joke = joke, onClick = { onJokeClick(joke) })
        }
    }
}


@Composable
fun JokeItem(joke: Joke, onClick: () -> Unit) { // Regular lambda
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Modifier.clickable expects a regular lambda
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Category: ${joke.category}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${joke.type}", style = MaterialTheme.typography.bodySmall)
            if (joke.type == "single") {
                Text(text = "Joke: ${joke.joke}", style = MaterialTheme.typography.bodyLarge)
            } else {
                Text(text = "Setup: ${joke.setup}", style = MaterialTheme.typography.bodyLarge)
            }
            joke.flags.forEach { (flag, value) ->
                Text(text = "$flag: $value", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
