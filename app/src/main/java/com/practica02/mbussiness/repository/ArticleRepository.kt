package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Article

class ArticleRepository private constructor() : FirestoreRepository<Article>(Article::class.java) {

    companion object {
        val instance: ArticleRepository = ArticleRepository()
        private const val NAME = "name"
        private const val UNITARY_PRICE = "unitaryPrice"

        fun filterByName(query: Query, value: String): Query =
            instance.filterByFieldValue(query, ArticleRepository.NAME, value)

        fun orderAscendingByName(query: Query): Query =
            instance.oderAscendingBy(query, ArticleRepository.NAME)

        fun orderDescendingByName(query: Query): Query =
            instance.oderDescendingBy(query, ArticleRepository.NAME)

        fun orderAscendingByUnitaryPrice(query: Query): Query =
            instance.oderAscendingBy(query, ArticleRepository.UNITARY_PRICE)

        fun orderDescendingByUnitaryPrice(query: Query): Query =
            instance.oderDescendingBy(query, ArticleRepository.UNITARY_PRICE)
    }
}