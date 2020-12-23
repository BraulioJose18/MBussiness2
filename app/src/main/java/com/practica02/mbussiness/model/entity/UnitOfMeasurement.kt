package com.practica02.mbussiness.model.entity

class UnitOfMeasurement(
    identifier: String = "",
    val name: String = "",
    registryState: String = "A"
) : DatabaseRegistry(identifier, registryState) {
    override fun toString(): String {
        return "UnitOfMeasurment(name='$name') ${super.toString()}"
    }
}
