package com.practica02.mbussiness.model.dto

data class BrandDTO(
    override val identifier: String = "",
    val name: String,
    override val registryState: String,
) : DatabaseRegistryDTO(identifier, registryState)