package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query

interface RequirementsRepository {

    companion object {
        const val ACTIVE: String = "A"
        const val INACTIVE: String = "I"
        const val ELIMINATED: String = "*"
    }

    // Filters
    fun filterByRegistryState(query: Query, field: String, values: Array<String>): Query
    fun filterByFieldValue(query: Query, field: String, value: String): Query

    //Order By
    fun oderAscendingBy(query: Query, field: String): Query
    fun oderDescendingBy(query: Query, field: String): Query

}