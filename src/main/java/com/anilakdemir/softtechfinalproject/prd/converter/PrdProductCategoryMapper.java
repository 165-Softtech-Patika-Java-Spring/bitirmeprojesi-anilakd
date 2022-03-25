package com.anilakdemir.softtechfinalproject.prd.converter;

import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategorySaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author anilakdemir
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrdProductCategoryMapper {

    PrdProductCategoryMapper INSTANCE = Mappers.getMapper(PrdProductCategoryMapper.class);

    PrdProductCategory convertToPrdProductCategory (PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto);

    PrdProductCategoryDto convertToPrdProductCategoryDto (PrdProductCategory prdProductCategory);

}
