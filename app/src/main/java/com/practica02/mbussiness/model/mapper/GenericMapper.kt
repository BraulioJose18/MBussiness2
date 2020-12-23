package com.practica02.mbussiness.model.mapper

interface GenericMapper<Entity, DTO> {
    fun entityToDto(entity: Entity): DTO
    fun dtoToEntity(dto: DTO): Entity
}