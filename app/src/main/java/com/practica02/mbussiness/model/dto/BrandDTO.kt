package com.practica02.mbussiness.model.dto

data class BrandDTO(
    override var identifier: String = "",
    var name: String,
    override var registryState: String,
) : DatabaseRegistryDTO(identifier, registryState)