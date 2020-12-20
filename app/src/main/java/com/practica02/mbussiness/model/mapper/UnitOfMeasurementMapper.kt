package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO
import com.practica02.mbussiness.model.entity.UnitOfMeasurement
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(uses = [ArticleMapper::class])
interface UnitOfMeasurementMapper : GenericMapper<UnitOfMeasurement, UnitOfMeasurementDTO> {
    @Mappings(
        value = [
            Mapping(target = "identifier", source = "entity.identifier"),
            Mapping(target = "name", source = "entity.name"),
            Mapping(target = "registryState", source = "entity.registryState")
        ]
    )
    override fun entityToDto(entity: UnitOfMeasurement): UnitOfMeasurementDTO;

    @InheritInverseConfiguration(name = "entityToDto")
    override fun dtoToEntity(dto: UnitOfMeasurementDTO): UnitOfMeasurement;

}