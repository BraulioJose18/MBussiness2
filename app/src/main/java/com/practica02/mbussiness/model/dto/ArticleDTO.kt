package com.practica02.mbussiness.model.dto

import com.google.firebase.firestore.Exclude

data class ArticleDTO(
    override val identifier: String = "",
    val name: String,
    val unitaryPrice: String,
    override val registryState: String,
) : DatabaseRegistryDTO(identifier, registryState) {

    lateinit var brand: BrandDTO
    lateinit var unitOfMeasurement: UnitOfMeasurementDTO

    constructor(
        identifier: String,
        name: String,
        unitaryPrice: String,
        registryState: String,
        brandDTO: BrandDTO,
        unitOfMeasurementDTO: UnitOfMeasurementDTO
    ) : this(identifier, name, unitaryPrice, registryState) {
        this.brand = brandDTO
        this.unitOfMeasurement = unitOfMeasurementDTO
    }
}