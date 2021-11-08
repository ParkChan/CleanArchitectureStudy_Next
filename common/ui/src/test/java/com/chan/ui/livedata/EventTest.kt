package com.chan.ui.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.chan.ui.util.InstantExecutorExtension
import com.chan.ui.util.getOrAwaitValue
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class EventTest {

    private val liveData = MutableLiveData<Event<String>>()
    private val owner: LifecycleOwner = mockk(relaxed = true)

    @Test
    fun `화면 회전등의 뷰파기로 인한 observe 가 전달 되지 않도록 합니다`() {
        liveData.observeEvent(owner, {
            println("전달받은 값은 $it")
        })

        val data1 = "value A"
        val data2 = "post B"

        liveData.value = Event(data1)

        assertEquals(
            data1,
            liveData.getOrAwaitValue().value
        )
        liveData.postValue(Event(data2))
        assertEquals(
            data2,
            liveData.getOrAwaitValue().value
        )
    }
}