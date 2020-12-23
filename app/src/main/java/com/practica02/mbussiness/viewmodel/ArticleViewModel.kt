package com.practica02.mbussiness.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.practica02.mbussiness.model.entity.Article
import com.practica02.mbussiness.repository.ArticleRepository
import com.practica02.mbussiness.repository.liveData.DocumentReferenceLiveData
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData

class ArticleViewModel : ViewModel() {

    private val repository: ArticleRepository by lazy { ArticleRepository.instance }
    val builderQuery: Query by lazy { this.repository.findAll() }
    val allArticleLiveData: MultipleDocumentReferenceLiveData<Article, Query> by lazy {
        MultipleDocumentReferenceLiveData(
            this.repository.findAll(),
            Article::class.java
        )
    }

    fun queryLiveData(queryBuilder: Query): MultipleDocumentReferenceLiveData<Article, Query> =
        MultipleDocumentReferenceLiveData(queryBuilder, Article::class.java)

    fun findById(identifier: String): DocumentReferenceLiveData<Article> =
        DocumentReferenceLiveData<Article>(
            this.repository.findById(identifier),
            Article::class.java
        )

    fun save(entity: Article): Task<Void> =
        this.repository.save(entity)

    fun update(entity: Article): Task<Void> =
        this.repository.update(entity)

    fun delete(entity: Article): Task<Void> =
        this.repository.delete(entity)

}