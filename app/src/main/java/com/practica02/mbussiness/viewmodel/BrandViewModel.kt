package com.practica02.mbussiness.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Brand
import com.practica02.mbussiness.repository.BrandRepository
import com.practica02.mbussiness.repository.liveData.DocumentReferenceLiveData
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData

class BrandViewModel : ViewModel() {

    private val repository: BrandRepository by lazy { BrandRepository.instance }
    val builderQuery: Query by lazy { this.repository.findAll() }
    val brandLiveData: MultipleDocumentReferenceLiveData<Brand, Query> by lazy {
        MultipleDocumentReferenceLiveData(
            this.repository.findAll(),
            Brand::class.java
        )
    }

    fun queryLiveData(queryBuilder: Query): MultipleDocumentReferenceLiveData<Brand, Query> =
        MultipleDocumentReferenceLiveData(queryBuilder, Brand::class.java)

    fun findById(identifier: String): DocumentReferenceLiveData<Brand> =
        DocumentReferenceLiveData<Brand>(this.repository.findById(identifier), Brand::class.java)

    fun save(entity: Brand): Task<Void> =
        this.repository.save(entity)

    fun update(entity: Brand): Task<Void> =
        this.repository.update(entity)

    fun delete(entity: Brand): Task<Void> =
        this.repository.delete(entity)
}