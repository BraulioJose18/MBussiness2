package com.practica02.mbussiness.model.dto

abstract class DatabaseRegistryDTO(
    open var identifier: String,
    open var registryState: String
) {
    override fun toString(): String {
        return "DatabaseRegistryDTO(identifier='$identifier', registryState='$registryState')"
    }
}
