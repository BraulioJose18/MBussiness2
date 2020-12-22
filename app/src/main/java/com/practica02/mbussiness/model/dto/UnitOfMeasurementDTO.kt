package com.practica02.mbussiness.model.dto

data class UnitOfMeasurementDTO(
    override var identifier: String = "",
    val name: String,
    override var registryState: String,
) : DatabaseRegistryDTO(identifier, registryState)
