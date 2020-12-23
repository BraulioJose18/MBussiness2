package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.ArticleDTO
import com.practica02.mbussiness.model.entity.Article

class ArticleMapper private constructor() :
    GenericMapper<Article, ArticleDTO> {

    companion object {
        @JvmStatic
        val mapper: ArticleMapper = ArticleMapper()
    }

    override fun entityToDto(entity: Article): ArticleDTO =
        ArticleDTO(
            entity.identifier,
            entity.name,
            entity.unitaryPrice.toString(),
            entity.registryState,
            entity.brandId,
            entity.unitOfMeasurementId
        )


    override fun dtoToEntity(dto: ArticleDTO): Article =
        Article(
            dto.identifier,
            dto.name,
            dto.unitaryPrice.toDouble(),
            dto.registryState,
            dto.brandId,
            dto.unitOfMeasurementId
        )

}