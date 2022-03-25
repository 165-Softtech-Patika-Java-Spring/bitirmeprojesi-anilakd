package com.anilakdemir.softtechfinalproject.usr.controller;

import com.anilakdemir.softtechfinalproject.BaseTest;
import com.anilakdemir.softtechfinalproject.usr.dto.UsrUserUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UsrUserControllerTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/users";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    void setUp () {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void update () throws Exception {

        UsrUserUpdateRequestDto usrUserUpdateRequestDto = new UsrUserUpdateRequestDto();
        usrUserUpdateRequestDto.setId(1L);
        usrUserUpdateRequestDto.setName("name");
        usrUserUpdateRequestDto.setUsername("surname");
        usrUserUpdateRequestDto.setSurname("username");
        usrUserUpdateRequestDto.setPassword("1234");

        String content = objectMapper.writeValueAsString(usrUserUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void deleteTest () throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/2202").content("2202").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }
}