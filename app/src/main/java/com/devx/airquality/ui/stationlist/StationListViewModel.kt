package com.devx.airquality.ui.stationlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.airquality.logic.GetStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationListViewModel @Inject constructor(private val getStationsUseCase: GetStationsUseCase) :
    ViewModel() {

    var state by mutableStateOf(
        State(stations = listOf())
    )

    init {
        viewModelScope.launch {
            loadStations()
        }
    }

    private suspend fun loadStations() {
        val stations = getStationsUseCase()
        state = State(stations.map {
            it.name
        })
    }

    data class State(
        val stations: List<String> = listOf()
    )
}