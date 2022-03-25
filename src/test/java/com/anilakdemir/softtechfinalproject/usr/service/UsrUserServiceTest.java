package com.anilakdemir.softtechfinalproject.usr.service;

import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserSaveRequestDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import com.anilakdemir.softtechfinalproject.usr.exceptions.UsrUserErrorMessage;
import com.anilakdemir.softtechfinalproject.usr.service.entityService.UsrUserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
class UsrUserServiceTest {

    @InjectMocks
    private UsrUserService usrUserService;

    @Mock
    private UsrUserEntityService usrUserEntityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldNotSaveWhenSaveRequestParameterIsNull () {

        assertThrows(NullPointerException.class, () -> usrUserService.save(null));

    }

    @Test
    void shouldSave () {

        String password = "password";
        String hashedPassword = "hashedPassword";

        UsrUserSaveRequestDto usrUserSaveRequestDto = mock(UsrUserSaveRequestDto.class);
        when(usrUserSaveRequestDto.getPassword()).thenReturn(password);

        UsrUser usrUser = mock(UsrUser.class);
        when(usrUser.getId()).thenReturn(1L);

        when(passwordEncoder.encode(anyString())).thenReturn(hashedPassword);
        when(usrUserEntityService.saveWithControlUsername(any())).thenReturn(usrUser);

        UsrUserDto result = usrUserService.save(usrUserSaveRequestDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldNotSave_whenUsernameIsDuplicated () {

        UsrUserSaveRequestDto usrUserSaveRequestDto = mock(UsrUserSaveRequestDto.class);

        when(usrUserEntityService.saveWithControlUsername(any())).thenThrow(new DuplicateException(UsrUserErrorMessage.USERNAME_ALREADY_EXIST));

        assertThrows(DuplicateException.class, () -> usrUserService.save(usrUserSaveRequestDto));

    }

    @Test
    void shouldNotUpdateWhenUpdateRequestParameterIsNull () {

        assertThrows(NullPointerException.class, () -> usrUserService.update(null));

    }

    @Test
    void shouldUpdate () {

        UsrUserUpdateRequestDto usrUserUpdateRequestDto = mock(UsrUserUpdateRequestDto.class);
        when(usrUserUpdateRequestDto.getId()).thenReturn(1L);
        when(usrUserUpdateRequestDto.getUsername()).thenReturn("username");
        when(usrUserUpdateRequestDto.getPassword()).thenReturn("password");

        UsrUser usrUser = mock(UsrUser.class);
        when(usrUser.getId()).thenReturn(1L);

        when(usrUserEntityService.existsById(anyLong())).thenReturn(true);

        Optional<UsrUser> optionalUsrUser = Optional.of(usrUser);
        optionalUsrUser.get().setId(1L);

        when(usrUserEntityService.findByUsername(anyString())).thenReturn(optionalUsrUser);


        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(usrUserEntityService.save(any())).thenReturn(usrUser);


        UsrUserDto update = usrUserService.update(usrUserUpdateRequestDto);

        assertEquals(usrUserUpdateRequestDto.getId(), update.getId());


    }

    @Test
    void shouldNotUpdate_whenUserIsNotExist () {

        UsrUserUpdateRequestDto usrUserUpdateRequestDto = mock(UsrUserUpdateRequestDto.class);

        when(usrUserUpdateRequestDto.getId()).thenReturn(1L);

        when(usrUserEntityService.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> usrUserService.update(usrUserUpdateRequestDto));

    }

    @Test
    void shouldNotUpdate_whenUsernameInUseByAnotherUser () {

        UsrUserUpdateRequestDto usrUserUpdateRequestDto = mock(UsrUserUpdateRequestDto.class);
        when(usrUserUpdateRequestDto.getId()).thenReturn(1L);
        when(usrUserUpdateRequestDto.getUsername()).thenReturn("username");

        UsrUser usrUser = mock(UsrUser.class);
        when(usrUser.getId()).thenReturn(2L);

        Optional<UsrUser> optionalUsrUser = Optional.of(usrUser);

        when(usrUserEntityService.findByUsername(anyString())).thenReturn(optionalUsrUser);

        when(usrUserEntityService.existsById(anyLong())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> usrUserService.update(usrUserUpdateRequestDto));

    }

    @Test
    void shouldNotUpdateWhenIdParameterIsNull () {

        assertThrows(Exception.class, () -> usrUserService.deleteById(null));
    }

    @Test
    void shouldDeleteById () {

        Long id = 1L;

        when(usrUserEntityService.existsById(id)).thenReturn(true);

        doNothing().when(usrUserEntityService).deleteById(id);

        usrUserService.deleteById(id);

        verify(usrUserEntityService).deleteById(id);
    }

    @Test
    void shouldNotDeleteById_whenUserDoesNotExist () {

        Long id = 1L;

        when(usrUserEntityService.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> usrUserService.deleteById(id));
    }
}