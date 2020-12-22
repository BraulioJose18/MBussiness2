package com.practica02.mbussiness.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.DatabaseRegistry

abstract class FirestoreRepository<E : DatabaseRegistry>(
    private val entityClass: Class<E>
) : CrudRepository<String, E> {

    companion object : RequirementsRepository {
        private const val REGISTRY_STATE = "registryState"
        override fun filterByRegistryStates(query: Query, values: List<String>): Query =
            query.whereIn(REGISTRY_STATE, values)

        override fun filterByFieldValue(query: Query, field: String, value: String): Query =
            query.whereEqualTo(field, value)

        override fun orderAscendingBy(query: Query, field: String): Query =
            query.orderBy(field, Query.Direction.ASCENDING)

        override fun orderDescendingBy(query: Query, field: String): Query =
            query.orderBy(field, Query.Direction.DESCENDING)

        override fun orderAscendingByRegistryState(query: Query): Query =
            query.orderBy(REGISTRY_STATE, Query.Direction.ASCENDING)

        override fun orderDescendingByRegistryState(query: Query): Query =
            query.orderBy(REGISTRY_STATE, Query.Direction.DESCENDING)

        override fun filterByRegistryState(query: Query, value: String): Query =
            query.whereEqualTo(REGISTRY_STATE, value)

    }

    private val collection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(this.entityClass.simpleName)
    }

    override fun save(entity: E): Task<Void> =
        this.collection.document().set(entity)

    override fun update(entity: E): Task<Void> =
        this.collection.document(entity.identifier).set(entity)

    override fun delete(entity: E): Task<Void> =
        this.collection.document(entity.identifier).delete()

    override fun findById(identifier: String): DocumentReference =
        this.collection.document(identifier)

    override fun findAll(): Query =
        this.collection
}