package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Article

class ArticleRepository private constructor() : FirestoreRepository<Article>(Article::class.java) {

    companion object {
        val instance: ArticleRepository = ArticleRepository()
        private const val NAME = "name"
        private const val UNITARY_PRICE = "unitaryPrice"

        fun filterByName(query: Query, value: String): Query =
            FirestoreRepository.filterByFieldValue(query, ArticleRepository.NAME, value)

        fun orderAscendingByName(query: Query): Query =
            FirestoreRepository.orderAscendingBy(query, ArticleRepository.NAME)

        fun orderDescendingByName(query: Query): Query =
            FirestoreRepository.orderDescendingBy(query, ArticleRepository.NAME)

        fun orderAscendingByUnitaryPrice(query: Query): Query =
            FirestoreRepository.orderAscendingBy(query, ArticleRepository.UNITARY_PRICE)

        fun orderDescendingByUnitaryPrice(query: Query): Query =
            FirestoreRepository.orderDescendingBy(query, ArticleRepository.UNITARY_PRICE)
    }
}