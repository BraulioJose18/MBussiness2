package com.practica02.mbussiness.repository.liveData

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.practica02.mbussiness.model.entity.DatabaseRegistry

open class MultipleDocumentReferenceLiveData<E : DatabaseRegistry, out L : Query>(
    private val multipleReference: L,
    private val entityClass: Class<E>,
) : MutableLiveData<List<E>>(), EventListener<QuerySnapshot> {

    private val tag: String = MultipleDocumentReferenceLiveData::class.simpleName!!
    private lateinit var listener: ListenerRegistration

    override fun onActive() {
        this.listener = this.multipleReference.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        this.listener.remove()
        super.onInactive()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onEvent(querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (querySnapshot != null && !querySnapshot.isEmpty) {
            Log.e(this.tag, "Updating data")
            querySnapshot.toObjects(this.entityClass).also {
                for (i in 0 until it.size)
                    it[i].identifier = querySnapshot.documents[i].id
            }.let {
                this.value = it
            }
        } else if (error != null)
            Log.e(this.tag, error.message, error.cause)
    }

}