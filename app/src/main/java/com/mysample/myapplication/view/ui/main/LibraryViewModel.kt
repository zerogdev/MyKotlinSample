package com.mysample.myapplication.view.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber

class LibraryViewModel : ViewModel() {

    fun test() {
        Timber.d(">test start")
        viewModelScope.launch {
            Timber.d(">launch start (curThread=${Thread.currentThread()})")
            delay(5000)
            fetchDocs()
            Timber.d(">launch end")
        }
        Timber.d(">test end")
    }

    suspend fun fetchDocs() {
        Timber.d(">fetchDocs start (curThread=${Thread.currentThread()})")
        val result = get("http://api.test")
        Timber.d(">fetchDocs end")

    }
    suspend fun get(url:String) = withContext(Dispatchers.IO) {
        Timber.d(">delay start (curThread=${Thread.currentThread()})")
        delay(5000)
        Timber.d(">delay end")
    }

    fun testCancel() {
        viewModelScope.launch {
            val job = launch  {
                repeat(1000) {i ->
                    Timber.d("job: I'm sleeping $i ...")
                    delay(500L)
                }
            }
            delay(1300L) // delay a bit
            Timber.d("main: I'm tired of waiting!")
            job.cancel() // cancels the job
            job.join() // waits for job's completion
            Timber.d("main: Now I can quit.")
        }
    }

    fun testSuspendCancellableCoroutine() {
        viewModelScope.launch {
            val job = launch  {
                try {
                    cancellableCoroutine()
                } catch ( e: CancellationException) {
                    Timber.d("cancelable!!")
                }

            }
            delay(1300L) // delay a bit
            Timber.d("main: I'm tired of waiting!")
            job.cancel() // cancels the job
            job.join() // waits for job's completion
            Timber.d("main: Now I can quit.")
        }
    }

    suspend fun cancellableCoroutine() = suspendCancellableCoroutine<Int> {
        repeat(100000000) {

        }
    }
}