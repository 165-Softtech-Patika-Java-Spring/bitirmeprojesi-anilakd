package com.anilakdemir.softtechfinalproject.sec.controller;

import com.anilakdemir.softtechfinalproject.gen.dto.RestResponse;
import com.anilakdemir.softtechfinalproject.sec.dto.SecLoginRequestDto;
import com.anilakdemir.softtechfinalproject.sec.service.AuthenticationService;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller", summary = "Login and get token")
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody SecLoginRequestDto secLoginRequestDto) {

        String token = authenticationService.login(secLoginRequestDto);

        return ResponseEntity.ok(RestResponse.of(token));
    }

    @Operation(tags = "Authentication Controller", summary = "Register")
    @PostMapping("/register")
    public ResponseEntity register (@RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto) {

        UsrUserDto usrUserDto = authenticationService.register(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));
    }
}
