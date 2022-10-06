package com.example.themeal.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themeal.util.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _isShowLoading = MutableLiveData(false)
    val isShowLoading: LiveData<Boolean> get() = _isShowLoading

    protected fun <T> launchAsync(
        request: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {}
    ) {
        showLoading()
        viewModelScope.launch {
            when (val response = request(this)) {
                is DataResult.Success -> {
                    onSuccess(response.data)
                    hideLoading()
                }
                is DataResult.Error -> {
                    onError(response.exception)
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        _isShowLoading.value = true
    }

    private fun hideLoading() {
        _isShowLoading.value = false
    }
}
