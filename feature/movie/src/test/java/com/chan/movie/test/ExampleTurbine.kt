package com.chan.movie.test

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://github.com/cashapp/turbine
 */
class ExampleTurbine {

    @Test
    fun basicTest1() = runBlocking {
        flowOf("one", "two").test {
            assertEquals("one", awaitItem())
            assertEquals("two", awaitItem())
            awaitComplete()
        }
    }
    @Test
    fun basicTest2() = runBlocking {
        flowOf("one", "two").test {
            assertEquals("one", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

}