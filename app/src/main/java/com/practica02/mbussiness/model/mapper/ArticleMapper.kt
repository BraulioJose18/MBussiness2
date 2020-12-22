package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.ArticleDTO
import com.practica02.mbussiness.model.entity.Article

class ArticleMapper private constructor() :
    GenericMapper<Article, ArticleDTO> {

    companion object {
        @JvmStatic
        val mapper: ArticleMapper = ArticleMapper()
    }

    private val brandMapper: BrandMapper by lazy { BrandMapper.mapper }
    private val unitOfMeasurementMapper: UnitOfMeasurementMapper by lazy { UnitOfMeasurementMapper.mapper }

    override fun entityToDto(entity: Article): ArticleDTO =
        ArticleDTO(
            entity.identifier,
            entity.name,
            entity.unitaryPrice.toString(),
            entity.registryState,
            brandMapper.entityToDto(entity.brand),
            unitOfMeasurementMapper.entityToDto(entity.unitOfMeasurement)
        )


    override fun dtoToEntity(dto: ArticleDTO): Article =
        Article(
            dto.identifier,
            dto.name,
            dto.unitaryPrice.toDouble(),
            dto.registryState,
            brandMapper.dtoToEntity(dto.brand),
            unitOfMeasurementMapper.dtoToEntity(dto.unitOfMeasurement)
        )

}