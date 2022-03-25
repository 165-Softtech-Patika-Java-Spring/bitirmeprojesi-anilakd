package com.anilakdemir.softtechfinalproject.prd.converter;

import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductSaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author anilakdemir
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrdProductMapper {

    PrdProductMapper INSTANCE = Mappers.getMapper(PrdProductMapper.class);

    PrdProduct convertToPrdProduct (PrdProductSaveRequestDto prdProductSaveRequestDto);

    PrdProductDto convertToPrdProductDto (PrdProduct prdProduct);

    PrdProduct convertToPrdProduct (PrdProductUpdateRequestDto prdProductUpdateRequestDto);

    List<PrdProductDto> convertToPrdProductDtoList (List<PrdProduct> prdProductList);
}
