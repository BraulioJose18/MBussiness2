package com.practica02.mbussiness.model.entity

import com.google.firebase.firestore.Exclude

abstract class DatabaseRegistry(
    @get:Exclude
    public open var identifier: String,
    public open val registryState: String
) {
    override fun toString(): String {
        return "DatabaseRegistry(identifier='$identifier', registryState='$registryState')"
    }
}