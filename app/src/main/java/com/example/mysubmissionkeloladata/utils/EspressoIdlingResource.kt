package com.example.mysubmissionkeloladata.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val resource = "RESOURCE"
    private val espressoTestIdlingResource = CountingIdlingResource(resource)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }

    fun getEsspressoIdlingResource(): IdlingResource = espressoTestIdlingResource
}