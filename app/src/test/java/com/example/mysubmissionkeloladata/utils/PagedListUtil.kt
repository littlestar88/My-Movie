package com.example.mysubmissionkeloladata.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.stubbing.Answer

object PagedListUtil {
    @Suppress("UNCHECKED_CAST")
    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList: PagedList<*> = Mockito.mock(PagedList::class.java)

        val answer: Answer<T> = Answer {
            val index = it.arguments[0] as Int
            list[index]
        }

        Mockito.`when`<T>(pagedList[ArgumentMatchers.anyInt()] as T).thenAnswer(answer)
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList as PagedList<T>
    }
}