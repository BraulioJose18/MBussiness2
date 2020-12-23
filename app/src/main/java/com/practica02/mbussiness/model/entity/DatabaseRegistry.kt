package com.practica02.mbussiness.model.entity

import com.google.firebase.firestore.Exclude

abstract class DatabaseRegistry(
    @get:Exclude
    open var identifier: String,
    open val registryState: String
) {
    override fun toString(): String {
        return "DatabaseRegistry(identifier='$identifier', registryState='$registryState')"
    }
}