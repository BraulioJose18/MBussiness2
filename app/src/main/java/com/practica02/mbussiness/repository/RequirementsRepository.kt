package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query

interface RequirementsRepository {
    companion object {
        const val ACTIVE: String = "A"
        const val INACTIVE: String = "I"
        const val ELIMINATED: String = "*"
    }

    // Filters
    fun filterByRegistryStates(query: Query, values: List<String>): Query
    fun filterByRegistryState(query: Query, value: String): Query
    fun filterByFieldValue(query: Query, field: String, value: String): Query

    // Order
    fun oderAscendingBy(query: Query, field: String): Query
    fun oderDescendingBy(query: Query, field: String): Query
    fun oderAscendingByRegistryState(query: Query): Query
    fun oderDescendingByRegistryState(query: Query): Query
}