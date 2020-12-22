package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.ArticleDTO
import com.practica02.mbussiness.model.entity.Article
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers

@Mapper(uses = [BrandMapper::class, UnitOfMeasurementMapper::class])
interface ArticleMapper : GenericMapper<Article, ArticleDTO> {

    companion object {
        @JvmStatic
        val mapper: ArticleMapper = Mappers.getMapper(ArticleMapper::class.java)
    }

    @Mappings(
        value = [
            Mapping(target = "identifier", source = "entity.identifier"),
            Mapping(target = "name", source = "entity.name"),
            Mapping(target = "unitaryPrice", source = "entity.unitaryPrice"),
            Mapping(target = "registryState", source = "entity.registryState"),
            Mapping(target = "brand", source = "entity.brand"),
            Mapping(target = "unitOfMeasurement", source = "entity.unitOfMeasurement")
        ]
    )
    override fun entityToDto(entity: Article): ArticleDTO;

    @InheritInverseConfiguration(name = "entityToDto")
    override fun dtoToEntity(dto: ArticleDTO): Article;

}