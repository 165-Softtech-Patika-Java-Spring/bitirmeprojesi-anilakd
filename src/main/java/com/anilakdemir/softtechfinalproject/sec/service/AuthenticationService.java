package com.anilakdemir.softtechfinalproject.sec.service;

import com.anilakdemir.softtechfinalproject.sec.dto.SecLoginRequestDto;
import com.anilakdemir.softtechfinalproject.sec.enums.EnumJwtConstant;
import com.anilakdemir.softtechfinalproject.sec.security.JwtTokenProvider;
import com.anilakdemir.softtechfinalproject.sec.security.JwtUserDetails;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserSaveRequestDto;
import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import com.anilakdemir.softtechfinalproject.usr.service.UsrUserService;
import com.anilakdemir.softtechfinalproject.usr.service.entityService.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsrUserService usrUserService;
    private final UsrUserEntityService usrUserEntityService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UsrUserDto register (UsrUserSaveRequestDto usrUserSaveRequestDto) {

        UsrUserDto usrUserDto = usrUserService.save(usrUserSaveRequestDto);

        return usrUserDto;
    }

    public String login (SecLoginRequestDto secLoginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(secLoginRequestDto.getUsername(), secLoginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        String bearer = EnumJwtConstant.BEARER.getConstant();

        return bearer + token;
    }

    public UsrUser getCurrentCustomer () {

        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

        UsrUser usrUser = null;
        if (jwtUserDetails != null){
            usrUser = usrUserEntityService.findById(jwtUserDetails.getId());
        }

        return usrUser;
    }

    public Long getCurrentCustomerId () {

        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

        Long jwtUserDetailsId = null;
        if (jwtUserDetails != null){
            jwtUserDetailsId = jwtUserDetails.getId();
        }

        return jwtUserDetailsId;
    }

    private JwtUserDetails getCurrentJwtUserDetails () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails){
            jwtUserDetails = ( JwtUserDetails ) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }
}
