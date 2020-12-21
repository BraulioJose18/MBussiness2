package com.practica02.mbussiness.repository.liveData

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*

open class MultipleDocumentReference<E, out L : Query>(
    private val multipleReference: L,
    private val entityClass: Class<E>,
) : MutableLiveData<List<E>>(), EventListener<QuerySnapshot> {

    private val tag: String = MultipleDocumentReference::class.simpleName!!
    private lateinit var listener: ListenerRegistration

    override fun onActive() {
        this.listener = this.multipleReference.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        this.listener.remove()
        super.onInactive()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (querySnapshot != null && !querySnapshot.isEmpty) {
            Log.e(this.tag, "Updating data")
            querySnapshot.toObjects(entityClass).also { this.value = it }
        } else if (error != null)
            Log.e(this.tag, error.message, error.cause)
    }

}