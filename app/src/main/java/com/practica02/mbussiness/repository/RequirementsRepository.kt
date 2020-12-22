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
    fun orderAscendingBy(query: Query, field: String): Query
    fun orderDescendingBy(query: Query, field: String): Query
    fun orderAscendingByRegistryState(query: Query): Query
    fun orderDescendingByRegistryState(query: Query): Query
}