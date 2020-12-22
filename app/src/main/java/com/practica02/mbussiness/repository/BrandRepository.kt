package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Brand

class BrandRepository private constructor() : FirestoreRepository<Brand>(Brand::class.java) {

    companion object {
        val instance: BrandRepository by lazy { BrandRepository() }
        private const val NAME = "name"
        fun filterByName(query: Query, value: String): Query =
            FirestoreRepository.Companion.filterByFieldValue(query, BrandRepository.NAME, value)

        fun orderAscendingByName(query: Query): Query =
            FirestoreRepository.Companion.orderAscendingBy(query, BrandRepository.NAME)

        fun orderDescendingByName(query: Query): Query =
            FirestoreRepository.Companion.orderDescendingBy(query, BrandRepository.NAME)
    }


}