package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.BrandDTO
import com.practica02.mbussiness.model.entity.Brand
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers

@Mapper(uses = [ArticleMapper::class])
interface BrandMapper : GenericMapper<Brand, BrandDTO> {

    companion object {
        @JvmStatic
        val mapper: BrandMapper = Mappers.getMapper(BrandMapper::class.java)
    }

    @Mappings(
        value = [
            Mapping(target = "identifier", source = "entity.identifier"),
            Mapping(target = "name", source = "entity.name"),
            Mapping(target = "registryState", source = "entity.registryState")
        ]
    )
    override fun entityToDto(entity: Brand): BrandDTO;

    @InheritInverseConfiguration(name = "entityToDto")
    override fun dtoToEntity(dto: BrandDTO): Brand;

}