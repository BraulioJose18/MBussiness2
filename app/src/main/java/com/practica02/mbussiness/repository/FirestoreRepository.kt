package com.practica02.mbussiness.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.DatabaseRegistry

abstract class FirestoreRepository<E : DatabaseRegistry<String, Char>>(
    private val entityClass: Class<E>
) : CrudRepository<String, E>, RequirementsRepository {

    companion object {
        const val REGISTRY_STATE = "registryState"
    }

    private val collection: CollectionReference =
        FirebaseFirestore.getInstance().collection(this.entityClass.simpleName)

    override fun save(entity: E): Task<Void> =
        this.collection.document().set(entity)

    override fun update(entity: E): Task<Void> =
        this.collection.document(entity.identifier!!).set(entity)

    override fun delete(entity: E): Task<Void> =
        this.collection.document(entity.identifier!!).delete()

    override fun findById(identifier: String): DocumentReference =
        this.collection.document(identifier)

    override fun findAll(): Query =
        this.collection

    override fun filterByRegistryState(query: Query, field: String, values: Array<String>): Query =
        query.whereArrayContains(field, values)

    override fun filterByFieldValue(query: Query, field: String, value: String): Query =
        query.whereEqualTo(field, value)

    override fun oderAscendingBy(query: Query, field: String): Query =
        query.orderBy(field, Query.Direction.ASCENDING)

    override fun oderDescendingBy(query: Query, field: String): Query =
        query.orderBy(field, Query.Direction.DESCENDING)

    fun oderAscendingByRegistryState(query: Query): Query =
        query.orderBy(REGISTRY_STATE, Query.Direction.ASCENDING)

    fun oderDescendingByRegistryState(query: Query): Query =
        query.orderBy(REGISTRY_STATE, Query.Direction.DESCENDING)
}