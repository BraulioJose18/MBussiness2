package com.practica02.mbussiness.model.entity

data class Article(
    override val identifier: String,
    val name: String,
    val unitaryPrice: Double,
    override val registryState: Char,
    var brand: Brand,
    var unitOfMeasurement: UnitOfMeasurement,
) : DatabaseRegistry<String, Char>(identifier, registryState)
