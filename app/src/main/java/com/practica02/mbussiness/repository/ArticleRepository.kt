package com.practica02.mbussiness.repository

import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Article

class ArticleRepository : FirestoreRepository<Article>(Article::class.java) {

    companion object {
        public const val NAME = "name"
        public const val UNITARY_PRICE = "unitaryPrice"
    }

    fun filterByName(query: Query, value: String): Query =
        this.filterByFieldValue(query, NAME, value)

    fun orderAscendingByName(query: Query): Query =
        this.oderAscendingBy(query, NAME)

    fun orderDescendingByName(query: Query): Query =
        this.oderDescendingBy(query, NAME)

    fun orderAscendingByUnitaryPrice(query: Query): Query =
        this.oderAscendingBy(query, UNITARY_PRICE)

    fun orderDescendingByUnitaryPrice(query: Query): Query =
        this.oderDescendingBy(query, UNITARY_PRICE)

    // FALTA LLAMADAS A LAS RELACIONES EMBEBIDAS


}