package com.practica02.mbussiness.model.entity

import com.google.firebase.firestore.Exclude

class Article(
    identifier: String = "",
    val name: String,
    val unitaryPrice: Double,
    registryState: String,
) : DatabaseRegistry(
    identifier, registryState
) {
    @get:Exclude
    lateinit var brand: Brand

    @get:Exclude
    lateinit var unitOfMeasurement: UnitOfMeasurement

    constructor(
        identifier: String,
        name: String,
        unitaryPrice: Double,
        registryState: String,
        brand: Brand,
        unitOfMeasurement: UnitOfMeasurement
    ) : this(
        identifier,
        name,
        unitaryPrice,
        registryState
    ) {
        this.brand = brand
        this.unitOfMeasurement = unitOfMeasurement
    }
}