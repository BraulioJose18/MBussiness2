package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Brand

class BrandRepository private constructor()
    : FirestoreRepository<Brand>(Brand::class.java) {

    companion object {
        public val instance: BrandRepository = BrandRepository()
        public const val NAME = "name"
    }

    fun filterByName(query: Query, value: String): Query =
        this.filterByFieldValue(query, ArticleRepository.NAME, value)

    fun orderAscendingByName(query: Query): Query =
        this.oderAscendingBy(query, ArticleRepository.NAME)
}