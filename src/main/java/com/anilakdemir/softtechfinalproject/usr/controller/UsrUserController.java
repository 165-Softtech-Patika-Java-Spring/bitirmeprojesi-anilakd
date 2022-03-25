package com.anilakdemir.softtechfinalproject.usr.controller;

import com.anilakdemir.softtechfinalproject.gen.dto.RestResponse;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserDto;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsrUserController {

    private final UsrUserService usrUserService;

    @Operation(tags = "User Controller", summary = "Update user")
    @PutMapping
    public ResponseEntity update (@Valid @RequestBody UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        UsrUserDto usrUserDto = usrUserService.update(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));
    }

    @Operation(tags = "User Controller", summary = "Delete user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable Long id) {

        usrUserService.deleteById(id);

        return ResponseEntity.ok(RestResponse.empty());
    }
}
