package com.practica02.mbussiness.model.dto

data class UnitOfMeasurementDTO(
    override var identifier: String = "",
    var name: String,
    override var registryState: String,
) : DatabaseRegistryDTO(identifier, registryState)
