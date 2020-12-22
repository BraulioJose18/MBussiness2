package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.UnitOfMeasurement

class UnitOfMeasurementRepository private constructor() :
    FirestoreRepository<UnitOfMeasurement>(UnitOfMeasurement::class.java) {

    companion object {
        val instance: UnitOfMeasurementRepository by lazy { UnitOfMeasurementRepository() }
        private const val NAME = "name"
        fun filterByName(query: Query, value: String): Query =
            FirestoreRepository.Companion.filterByFieldValue(query, UnitOfMeasurementRepository.NAME, value)

        fun orderAscendingByName(query: Query): Query =
            FirestoreRepository.Companion.orderAscendingBy(query, UnitOfMeasurementRepository.NAME)
    }
}