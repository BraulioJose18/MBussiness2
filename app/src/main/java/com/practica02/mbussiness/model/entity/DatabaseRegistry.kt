package com.practica02.mbussiness.model.entity


open class DatabaseRegistry<I, R>(
    open val identifier: I,
    open val registryState: R
)
