package com.practica02.mbussiness.model.dto

import com.google.firebase.firestore.Exclude

data class BrandDTO(
    @get:Exclude
    override val identifier: String,
    val name: String,
    override val registryState: String,
) : DatabaseRegistryDTO(identifier, registryState)