package com.practica02.mbussiness.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.UnitOfMeasurement
import com.practica02.mbussiness.repository.FirestoreRepository
import com.practica02.mbussiness.repository.RequirementsRepository
import com.practica02.mbussiness.repository.UnitOfMeasurementRepository
import com.practica02.mbussiness.repository.liveData.DocumentReferenceLiveData
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData

class UnitOfMeasurementViewModel : ViewModel() {

    private val repository: UnitOfMeasurementRepository by lazy { UnitOfMeasurementRepository.instance }
    val builderQuery: Query by lazy { this.repository.findAll() }
    val allUnitLiveData: MultipleDocumentReferenceLiveData<UnitOfMeasurement, Query> by lazy {
        MultipleDocumentReferenceLiveData(
            this.repository.findAll(),
            UnitOfMeasurement::class.java
        )
    }

    val activeUnitLiveData: MultipleDocumentReferenceLiveData<UnitOfMeasurement, Query> by lazy {
        MultipleDocumentReferenceLiveData(
            FirestoreRepository.filterByRegistryState(this.repository.findAll(), RequirementsRepository.ACTIVE),
            UnitOfMeasurement::class.java
        )
    }

    fun queryLiveData(queryBuilder: Query): MultipleDocumentReferenceLiveData<UnitOfMeasurement, Query> =
        MultipleDocumentReferenceLiveData(queryBuilder, UnitOfMeasurement::class.java)

    fun findById(identifier: String): DocumentReferenceLiveData<UnitOfMeasurement> =
        DocumentReferenceLiveData<UnitOfMeasurement>(
            this.repository.findById(identifier),
            UnitOfMeasurement::class.java
        )

    fun save(entity: UnitOfMeasurement): Task<Void> =
        this.repository.save(entity)

    fun update(entity: UnitOfMeasurement): Task<Void> =
        this.repository.update(entity)

    fun delete(entity: UnitOfMeasurement): Task<Void> =
        this.repository.delete(entity)
}