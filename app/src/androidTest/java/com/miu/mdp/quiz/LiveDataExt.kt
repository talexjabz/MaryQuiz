package com.miu.mdp.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrWaitFor(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val countDownLatch = CountDownLatch(1)
    val observer = object: Observer<T> {
        override fun onChanged(t: T) {
            data = t
            countDownLatch.countDown() //Count down if the observer receives data.
            this@getOrWaitFor.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (!countDownLatch.await(time, timeUnit)) { //When the wait time expires, throw this exception.
        throw TimeoutException("Observation took longer than $time ")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
