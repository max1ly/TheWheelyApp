package com.max.wheely.domain

import java.util.*

data class Option(
    val message: String,
    val selected: Boolean = false,
    val id: UUID = UUID.randomUUID(),
)
