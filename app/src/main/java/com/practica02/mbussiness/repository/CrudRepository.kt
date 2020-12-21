package com.practica02.mbussiness.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

/**
 * @param I Entity key.
 * @param E Entity instance.
 */
interface CrudRepository<I, E> {
    fun save(entity: E): Task<Void>
    fun update(entity: E): Task<Void>
    fun delete(entity: E): Task<Void>
    fun findById(identifier: I): DocumentReference
    fun findAll(): Query
}