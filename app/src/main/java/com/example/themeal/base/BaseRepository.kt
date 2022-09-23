package com.example.themeal.base

import android.util.Log
import com.example.themeal.util.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

open class BaseRepository {

    suspend fun <T> getResult(request: suspend CoroutineScope.() -> T): DataResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                DataResult.Success(request())
            } catch (e: IOException) {
                Log.v("error", "Exception=[$e]")
                DataResult.Error(e)
            }
        }
    }
}
