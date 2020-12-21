package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query

interface RequirementsRepository {

    companion object {
        public const val ACTIVE: String = "A"
        public const val INACTIVE: String = "I"
        public const val ELIMINATED: String = "*"
    }

    // Filters
    fun filterByRegistryState(query: Query, field: String, values: Array<String>): Query
    fun filterByFieldValue(query: Query, field: String, value: String): Query

    //Order By
    fun oderAscendingBy(query: Query, field: String): Query
    fun oderDescendingBy(query: Query, field: String): Query

}