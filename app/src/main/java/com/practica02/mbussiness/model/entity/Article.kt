package com.practica02.mbussiness.model.entity

class Article(
    identifier: String = "",
    val name: String = "",
    val unitaryPrice: Double = 0.0,
    registryState: String = "",
    val brandId: String = "",
    val unitOfMeasurementId: String = ""
) : DatabaseRegistry(
    identifier, registryState
)