package com.practica02.mbussiness.model.dto

data class ArticleDTO(
    override var identifier: String = "",
    var name: String,
    var unitaryPrice: String,
    override var registryState: String,
    var brandId: String,
    var unitOfMeasurementId: String
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

    override fun toString(): String {
        return "ArticleDTO(identifier='$identifier', name='$name', unitaryPrice='$unitaryPrice', registryState='$registryState', brandId='$brandId', unitOfMeasurementId='$unitOfMeasurementId')"
    }


}