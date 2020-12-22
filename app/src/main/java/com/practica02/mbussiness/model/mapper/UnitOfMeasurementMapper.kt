package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO
import com.practica02.mbussiness.model.entity.UnitOfMeasurement

class UnitOfMeasurementMapper private constructor() :
    GenericMapper<UnitOfMeasurement, UnitOfMeasurementDTO> {

    companion object {
        @JvmStatic
        val mapper: UnitOfMeasurementMapper = UnitOfMeasurementMapper()
    }

    override fun entityToDto(entity: UnitOfMeasurement): UnitOfMeasurementDTO =
        UnitOfMeasurementDTO(entity.identifier, entity.name, entity.registryState)

    override fun dtoToEntity(dto: UnitOfMeasurementDTO): UnitOfMeasurement =
        UnitOfMeasurement(dto.identifier, dto.name, dto.registryState)

}