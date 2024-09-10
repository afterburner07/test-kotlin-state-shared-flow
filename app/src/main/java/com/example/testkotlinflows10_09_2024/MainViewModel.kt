package com.example.testkotlinflows10_09_2024

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkotlinflows10_09_2024.utils.ControlledRunner
import com.example.testkotlinflows10_09_2024.utils.SingleRunner
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel : ViewModel() {

    /*
        private val _text = MutableLiveData<String>().apply {
            value = "This is home Fragment"
        }
        val text: LiveData<String> = _text
    */

    val textAsStateFlow = MutableStateFlow("")
    val readOnlyTextAsStateFlow: StateFlow<String> = textAsStateFlow
    val updateTextAsSharedFlow = MutableSharedFlow<String>(0)
    val readOnlyUpdateTextAsSharedFlow: SharedFlow<String> = updateTextAsSharedFlow
    var controlledRunner = ControlledRunner<String>()
    val singleRunner = SingleRunner()

    fun updateTextFromStateFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            updateTextFromStateFlowActually()
        }
    }

    fun updateTextFromSharedFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            updateTextFromSharedFlowActually()
        }
    }

    private suspend fun updateTextFromStateFlowActually() {
//        textAsStateFlow.emit("Initial data")
        textAsStateFlow.value = "Initial data"
        delay(1.seconds)
//        textAsStateFlow.emit("Loading...")
        textAsStateFlow.value = "Loading..."
        delay(2.seconds)
//        textAsStateFlow.emit("Data fetched")
        textAsStateFlow.value = "Data fetched"
    }


    private suspend fun updateTextFromSharedFlowActually() {
        updateTextAsSharedFlow.emit("Initial data")
        delay(1.seconds)
        updateTextAsSharedFlow.emit("Loading...")
        delay(2.seconds)
        updateTextAsSharedFlow.emit("Data fetched")
    }
}