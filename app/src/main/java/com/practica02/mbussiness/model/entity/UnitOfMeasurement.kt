package com.practica02.mbussiness.model.entity

class UnitOfMeasurement(
    identifier: String = "",
    public val name: String = "",
    registryState: String = "A"
) : DatabaseRegistry(identifier, registryState) {
}
