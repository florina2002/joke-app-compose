package com.example.nechitaflorinas2b.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nechitaflorinas2b.data.Joke
import com.example.nechitaflorinas2b.data.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes: StateFlow<List<Joke>> = _jokes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _reportedJokes = MutableStateFlow<Set<Joke>>(emptySet())
    val reportedJokes: StateFlow<Set<Joke>> = _reportedJokes

    private fun fetchJokes(category: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getJokes(category = category)
            _jokes.value = response.jokes
        }
    }

    fun fetchJokesWithLoading(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            fetchJokes(category)
            _isLoading.value = false
        }
    }

    fun reportJoke(joke: Joke) {
        _reportedJokes.value += joke
    }
}
