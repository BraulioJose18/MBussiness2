package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query

interface RequirementsRepository {
    companion object {
        const val ACTIVE: String = "A"
        const val INACTIVE: String = "I"
        const val ELIMINATED: String = "*"
    }
}