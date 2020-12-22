package com.practica02.mbussiness.model.mapper

import com.practica02.mbussiness.model.dto.BrandDTO
import com.practica02.mbussiness.model.entity.Brand

class BrandMapper private constructor() :
    GenericMapper<Brand, BrandDTO> {

    companion object {
        @JvmStatic
        val mapper: BrandMapper = BrandMapper()
    }

    override fun entityToDto(entity: Brand): BrandDTO = BrandDTO(entity.identifier, entity.name, entity.registryState)

    override fun dtoToEntity(dto: BrandDTO): Brand = Brand(dto.identifier, dto.name, dto.registryState)

}