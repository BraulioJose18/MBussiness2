package com.practica02.mbussiness.model.entity

data class UnitOfMeasurement(
    override val identifier: String? = null,
    val name: String,
    override val registryState: Char
) : DatabaseRegistry<String, Char>(identifier, registryState)
