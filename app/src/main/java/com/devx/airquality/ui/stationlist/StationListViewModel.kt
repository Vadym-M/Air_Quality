package com.devx.airquality.ui.stationlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StationListViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(
        State(stations = listOf())
    )

    init {
        loadStations()
    }

    private fun loadStations() {
        state = State(stations = listOf("Word 1", "Word 2", "Word 3"))
    }

    data class State(
        val stations: List<String> = listOf()
    ){}
}