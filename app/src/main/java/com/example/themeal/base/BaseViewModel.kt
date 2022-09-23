package com.example.themeal.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themeal.util.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> launchAsync(
        request: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            when (val response = request(this)) {
                is DataResult.Success -> onSuccess(response.data)
                is DataResult.Error -> onError(response.exception)
            }
        }
    }
}
