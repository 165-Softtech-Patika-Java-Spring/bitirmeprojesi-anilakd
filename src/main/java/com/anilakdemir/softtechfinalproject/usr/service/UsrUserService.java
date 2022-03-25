package com.anilakdemir.softtechfinalproject.usr.service;

import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.usr.converter.UsrUserMapper;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserSaveRequestDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import com.anilakdemir.softtechfinalproject.usr.exceptions.UsrUserErrorMessage;
import com.anilakdemir.softtechfinalproject.usr.service.entityService.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class UsrUserService {

    private final UsrUserEntityService usrUserEntityService;
    private final PasswordEncoder passwordEncoder;

    public UsrUserDto save (UsrUserSaveRequestDto usrUserSaveRequestDto) {

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserSaveRequestDto);

        usrUser = encodeUserPassword(usrUser);

        usrUser = usrUserEntityService.saveWithControlUsername(usrUser);

        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);

        return usrUserDto;
    }

    public UsrUserDto update (UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        controlIsUserExist(usrUserUpdateRequestDto);

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserUpdateRequestDto);

        usrUser = encodeUserPassword(usrUser);

        usrUser = usrUserEntityService.save(usrUser);

        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);

        return usrUserDto;
    }

    public void deleteById (Long id) {

        if (!usrUserEntityService.existsById(id)){
            throw new EntityNotFoundException(UsrUserErrorMessage.USER_NOT_FOUND);
        }

        usrUserEntityService.deleteById(id);
    }

    private void controlIsUserExist (UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        Long id = usrUserUpdateRequestDto.getId();
        boolean isExist = usrUserEntityService.existsById(id);

        if (!isExist){
            throw new EntityNotFoundException(UsrUserErrorMessage.USER_NOT_FOUND);
        }

        String username = usrUserUpdateRequestDto.getUsername();
        Optional<UsrUser> usrUser = usrUserEntityService.findByUsername(username);


        if (usrUser.isPresent() && !Objects.equals(usrUser.get().getId(), id)){
            throw new DuplicateException(UsrUserErrorMessage.USERNAME_ALREADY_EXIST);
        }
    }

    private UsrUser encodeUserPassword (UsrUser usrUser) {
        String password = passwordEncoder.encode(usrUser.getPassword());
        usrUser.setPassword(password);
        return usrUser;
    }
}
