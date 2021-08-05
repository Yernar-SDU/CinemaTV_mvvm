package com.example.cinematv_mvvm.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    // Do work in IO
    fun <P> launchIO(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e("Coroutine-BaseViewModel", e.toString())
        }) {
            withContext(Dispatchers.IO) {
                doOnAsyncBlock.invoke(this)
            }
        }
    }
}