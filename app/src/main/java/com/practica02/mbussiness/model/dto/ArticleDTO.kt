package com.practica02.mbussiness.model.dto

import com.google.firebase.firestore.Exclude

data class ArticleDTO(
    @get:Exclude
    override val identifier: String? = null,
    val name: String,
    val unitaryPrice: String,
    override val registryState: String,
    @get:Exclude
    var brand: BrandDTO,
    @get:Exclude
    var unitOfMeasurement: UnitOfMeasurementDTO,
) : DatabaseRegistryDTO(identifier, registryState)