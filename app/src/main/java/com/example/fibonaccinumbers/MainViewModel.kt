package com.example.fibonaccinumbers

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _counter = MutableLiveData<String>()
    val counter: LiveData<String>
        get() =  _counter
    private val _isCalculating = MutableLiveData<Boolean>(false)

    val isCalculating: LiveData<Boolean>
        get() =  _isCalculating
    private val mainHandler = Handler(Looper.getMainLooper())
    private var calculationRunnable: Runnable? = null
    fun onStateButtonClick(input : String){
        if(_isCalculating.value == true){
            cancelCalculation()
        }else{
            generate(input)
        }
    }
    fun cancelCalculation() {
        calculationRunnable?.let { mainHandler.removeCallbacks(it) }
        calculationRunnable = null
        _isCalculating.value = false
    }

    fun generate (input: String) {
        val number = input.trim().toIntOrNull()

        if (number == null) {
            _counter.value = "Invalid input"
            return
        }
        cancelCalculation()
        _isCalculating.value = true

        var step = 0
        var a = 0L
        var b = 1L

        calculationRunnable = object : Runnable {
            override fun run() {
                val current = when(step) {
                    0 -> a
                    1 -> b
                    else -> {
                        val next = a + b
                        a = b
                        b = next
                        b
                    }
                }

                if (step < number) {
                    _counter.value = "Current is $current"
                    step++
                    mainHandler.postDelayed(this, 200L)
                } else {
                    _counter.value = "Result is $current"
                    _isCalculating.value = false
                }
            }
        }
        mainHandler.post(calculationRunnable!!)
    }
    override fun onCleared() {
        super.onCleared()
        cancelCalculation()
    }
}