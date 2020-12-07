package com.mysample.myapplication.view.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

class LibraryViewModel : ViewModel() {

    fun test2() {
        viewModelScope.launch {
            Timber.d(">test 1")
            val s1 = s1()
            Timber.d(">test 2")
            val s2 = s2()
            Timber.d(">test 3 - ${s1} ${s2}")
        }
        Timber.d(">test 4")
        for ( i in 0 until 100000000000) {

        }
        Timber.d(">test 5")
    }

    suspend fun s1() : String {
        Timber.d(">test s1 1")
        delay(500)
        Timber.d(">test s1 2")
        return "s1"
    }

    suspend fun s2() : String {
        Timber.d(">test s2 1")
        delay(500)
        Timber.d(">test s2 2")
        return "s2"
    }

    fun test() {
        Timber.d(">test start (curThread=${Thread.currentThread()})")

        //async 로 코루틴 생성
//        val d = viewModelScope.async {
//            Timber.d(">async start (curThread=${Thread.currentThread()})")
////            delay(100)
//            for ( i in 0 until 100000) {
//                Timber.d(">async starting... (curThread=${Thread.currentThread()})")
//            }
//            Timber.d(">async end (curThread=${Thread.currentThread()})")
//        }

        //launch 로 코루틴 생ㅅㅇ
        val d2 = viewModelScope.launch {
            Timber.d(">launch start (curThread=${Thread.currentThread()})")
//            delay(5000)
            val d = async {
                Timber.d(">async start (curThread=${Thread.currentThread()})")
//            delay(100)
                for ( i in 0 until 100000) {
                    Timber.d(">async starting... (curThread=${Thread.currentThread()})")
                }
                Timber.d(">async end (curThread=${Thread.currentThread()})")
            }

            for ( i in 0 until 100000) {

            }
            fetchDocs()
            Timber.d(">launch end (curThread=${Thread.currentThread()})")
        }
//        Timber.d(">test roof start (curThread=${Thread.currentThread()})")
//        for ( i in 0 until 100000) {
//            Timber.d(">test roof starting... (curThread=${Thread.currentThread()})")
//        }
        Timber.d(">test end (curThread=${Thread.currentThread()})")
    }

    suspend fun fetchDocs() {
        Timber.d(">fetchDocs start (curThread=${Thread.currentThread()})")
        val result = get("http://api.test")
        Timber.d(">fetchDocs end")

    }
    suspend fun get(url:String) = withContext(Dispatchers.IO) {
        Timber.d(">delay start (curThread=${Thread.currentThread()})")
        delay(5000)
        Timber.d(">delay end (curThread=${Thread.currentThread()})")
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

        val d = viewModelScope.async {
            Timber.d(">async start (curThread=${Thread.currentThread()})")
        }
        if (d.isCancelled) {
            d.getCompletionExceptionOrNull()
        } else {
            d.getCompleted()
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

    fun testNonCancellableCoroutine() {
        viewModelScope.launch {
            val job = launch  {
                try {
                    repeat(10000) {i ->
                        Timber.d("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } finally {
                    withContext(NonCancellable) {
                        Timber.d("job: I'm running finally")
                        delay(1000L)
                        Timber.d("job: And I've just delayed for 1 sec because I'm non-cancellable")
                    }
                }

            }
            delay(1300L) // delay a bit
            Timber.d("main: I'm tired of waiting!")
            job.cancelAndJoin()
            Timber.d("main: Now I can quit.")
        }
    }

    fun testSequence() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                Timber.d("The answer is ${one + two}")
            }
            Timber.d("Completed in $time ms")
        }
    }

    fun testAsync() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                val one = async { doSomethingUsefulOne() }
                val two = async { doSomethingUsefulTwo() }
                Timber.d("The answer is ${one.await() + two.await()}")
            }
            Timber.d("Completed in $time ms")
        }

        viewModelScope.launch {
            val time = measureTimeMillis {
                //보통 생성 즉시 바로 실행이지만, LAZY로 하면 바로 실행하지않고 start()/join() 할때 실행
                val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
                // some computation
                one.start() // start the first one
                two.start() // start the second one
                Timber.d("The answer is ${one.await() + two.await()}")
            }
            Timber.d("Completed in $time ms")
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }


    //비동기 스타일 함수
    fun somethingUsefulOneAsync() = GlobalScope.async {
        doSomethingUsefulOne()
    }

    // The result type of somethingUsefulTwoAsync is Deferred<Int>
    fun somethingUsefulTwoAsync() = GlobalScope.async {
        doSomethingUsefulTwo()
    }

    /**
     * 취소에 불안정한 비동기 처리
     */
    fun testAsyncGlobalScope() {
        val time = measureTimeMillis {
            // we can initiate async actions outside of a coroutine
            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync()
            // but waiting for a result must involve either suspending or blocking.
            // here we use `runBlocking { ... }` to block the main thread while waiting for the result
            runBlocking {
                Timber.d("The answer is ${one.await() + two.await()}")
            }
        }
        Timber.d("Completed in $time ms")
    }

    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    /**
     * 코루틴 취소
     */
    fun testStructuredConcurrencyWithAsync() {
        viewModelScope.launch {
            //코루틴 범위에서 시작되어 취소된경우, 이 범위 코루틴이 모두 취소된다
            val time = measureTimeMillis {
                Timber.d("The answer is ${concurrentSum()}")
            }
            Timber.d("Completed in $time ms")
        }
    }

    /**
     * 비동기 취소한경우 처리
     */
    fun testFailedConcurrentSum() {
        viewModelScope.launch {
            try {
                failedConcurrentSum()
            } catch(e: ArithmeticException) {
                Timber.d("Computation failed with ArithmeticException")
            }
        }
    }

    suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // Emulates very long computation
                42
            } finally {
                Timber.d("First child was cancelled")
            }
        }
        val two = async<Int> {
            Timber.d("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }

    fun testLaunch() {
        viewModelScope.launch {
            delay(500)
            Timber.d("main runBlocking : I'm working in thread ${Thread.currentThread().name}")
        }
        viewModelScope.launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            delay(3000)
            Timber.d("Unconfined : I'm working in thread ${Thread.currentThread().name}")
        }
        viewModelScope.launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            delay(300)
            Timber.d("Default : I'm working in thread ${Thread.currentThread().name}")
        }
        viewModelScope.launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            delay(2000)
            Timber.d("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    fun testLaunchUnconfined() {
        viewModelScope.launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            Timber.d("[${Thread.currentThread().name}] Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            //delay 함수에서 사용된 스레드에서 이어서 실행됨
            Timber.d("[${Thread.currentThread().name}] Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }
        viewModelScope.launch { // context of the parent, main runBlocking coroutine
            Timber.d("[${Thread.currentThread().name}] main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            delay(1000)
            Timber.d("[${Thread.currentThread().name}] main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }
    }

    suspend fun log(msg: String) = Timber.d("[${coroutineContext[CoroutineName.Key]} ${Thread.currentThread().name}] $msg")

    fun testLog() = runBlocking<Unit> {
        val a = async {
            log("I'm computing a piece of the answer")
            6
        }
        val b = async {
            log("I'm computing another piece of the answer")
            7
        }
        log("The answer is ${a.await() * b.await()}")
    }

    fun testNewSingleThreadContext() {
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    log("Started in ctx1")
                    withContext(ctx2) {
                        log("Working in ctx2")
                    }
                    log("Back to ctx1")
                }
            }
        }
    }

    fun testCoroutineContext() = runBlocking<Unit> {
        log("My job is ${coroutineContext[Job]}")
    }

    fun testChildrenCoroutine() {
        viewModelScope.launch {
            // launch a coroutine to process some kind of incoming request
            val request = launch {
                // it spawns two other jobs, one with GlobalScope
                GlobalScope.launch {
                    log("job1: I run in GlobalScope and execute independently!")
                    delay(1000)
                    log("job1: I am not affected by cancellation of the request")
                }
                // and the other inherits the parent context
                launch {
                    delay(100)
                    log("job2: I am a child of the request coroutine")
                    delay(1000)
                    log("job2: I will not execute this line if my parent request is cancelled")
                }
            }
            delay(500)
            request.cancel() // cancel processing of the request
            delay(1000) // delay a second to see what happens
            log("main: Who has survived request cancellation?")
        }
    }

    fun testParentalResponsibilities() {
        viewModelScope.launch {
            // launch a coroutine to process some kind of incoming request
            val request = launch {
                repeat(3) { i -> // launch a few children jobs
                    launch {
                        log("Coroutine $i is delay")
                        delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                        log("Coroutine $i is done")
                    }
                }
                log("request: I'm done and I don't explicitly join my children that are still active")
            }
//            request.join() // wait for completion of the request, including all its children
            log("Now processing of the request is complete")
        }
    }

    fun testDebugLog() {
        viewModelScope.launch {
            log("Started main coroutine")
            // run two background value computations
            val v1 = async(CoroutineName("v1coroutine")) {
                delay(500)
                log("Computing v1")
                252
            }
            val v2 = async(CoroutineName("v2coroutine")) {
                delay(1000)
                log("Computing v2")
                6
            }
            log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
        }
    }

    fun error() {
        viewModelScope.launch {

        }.invokeOnCompletion { cause -> cause?.let {  } }
    }

    fun flowTest() {
        val f : Flow<Int> = flow {
            for (i in 1..100) {
                emit(i)
                log("emit $i")
            }
        }

        viewModelScope.launch {
            f.collect {
                log("collect $it")
            }
        }
    }


}