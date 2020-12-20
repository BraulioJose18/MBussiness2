package com.practica02.mbussiness.repository.liveData

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*

open class DocumentReferenceLiveData<E>(
    private val singleReference: DocumentReference,
    private val entityClass: Class<E>
) : MutableLiveData<E>(), EventListener<DocumentSnapshot> {

    private val TAG: String = DocumentReferenceLiveData::class.simpleName!!
    private lateinit var snapshotListener: ListenerRegistration

    override fun onActive() {
        this.snapshotListener = this.singleReference.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        this.snapshotListener.remove()
        super.onInactive()
    }

    override fun onEvent(documentSnapshot: DocumentSnapshot?, error: FirebaseFirestoreException?) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            Log.e(TAG, "Updating data")
            documentSnapshot.toObject(this.entityClass)!!.also { this.value = it }
        } else if (error != null)
            Log.e(TAG, error.message, error.cause)
    }

}