package com.practica02.mbussiness.model.entity

class Brand(
    identifier: String = "",
    val name: String = "",
    registryState: String = ""
) : DatabaseRegistry(identifier, registryState) {
    override fun toString(): String {
        return "Brand(name='$name') ${super.toString()}"
    }
}
