package com.practica02.mbussiness.model.dto

data class ArticleDTO(
    override val identifier: String,
    val name: String,
    override val registryState: String,
    var brand: BrandDTO,
    var unitOfMeasurement: UnitOfMeasurementDTO,
) : DatabaseRegistryDTO(identifier, registryState)