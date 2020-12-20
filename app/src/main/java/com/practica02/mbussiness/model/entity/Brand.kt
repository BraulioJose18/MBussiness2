package com.practica02.mbussiness.model.entity

data class Brand(
    override val identifier: String,
    val name: String,
    override val registryState: Char
) : DatabaseRegistry<String, Char>(identifier, registryState)
