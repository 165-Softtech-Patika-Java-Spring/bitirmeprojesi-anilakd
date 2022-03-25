package com.anilakdemir.softtechfinalproject.sec.security;

import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import com.anilakdemir.softtechfinalproject.usr.service.entityService.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsrUserEntityService usrUserEntityService;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {

        UsrUser usrUser = usrUserEntityService.findByUsernameWithControl(username);

        return JwtUserDetails.create(usrUser);
    }
}
