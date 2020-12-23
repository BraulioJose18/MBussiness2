package com.practica02.mbussiness.model.dto

data class ArticleDTO(
    override var identifier: String = "",
    var name: String,
    var unitaryPrice: String,
    override var registryState: String,
    val brandId: String,
    val unitOfMeasurementId: String
) : DatabaseRegistryDTO(identifier, registryState) {

    lateinit var brand: BrandDTO
    lateinit var unitOfMeasurement: UnitOfMeasurementDTO

    constructor(
        identifier: String,
        name: String,
        unitaryPrice: String,
        registryState: String,
        brandId: String,
        unitOfMeasurementId: String,
        brandDTO: BrandDTO,
        unitOfMeasurementDTO: UnitOfMeasurementDTO
    ) : this(identifier, name, unitaryPrice, registryState, brandId, unitOfMeasurementId) {
        this.brand = brandDTO
        this.unitOfMeasurement = unitOfMeasurementDTO
    }
}