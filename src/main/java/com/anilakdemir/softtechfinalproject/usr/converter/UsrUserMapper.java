package com.anilakdemir.softtechfinalproject.usr.converter;

import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserSaveRequestDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author anilakdemir
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsrUserMapper {

    UsrUserMapper INSTANCE = Mappers.getMapper(UsrUserMapper.class);

    UsrUser convertToUsrUser (UsrUserSaveRequestDto usrUserSaveRequestDto);

    UsrUserDto convertToUsrUserDto (UsrUser usrUser);

    UsrUser convertToUsrUser (UsrUserUpdateRequestDto usrUserUpdateRequestDto);

}
