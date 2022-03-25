package com.anilakdemir.softtechfinalproject.prd.controller;

import com.anilakdemir.softtechfinalproject.BaseTest;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategorySaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PrdProductCategoryControllerTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/product-categories";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    void setUp () {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void save () throws Exception {

        PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto = new PrdProductCategorySaveRequestDto();
        prdProductCategorySaveRequestDto.setCategoryType(PrdProductCategoryType.FOOD);
        prdProductCategorySaveRequestDto.setTaxRate(new BigDecimal("0.15"));

        String content = objectMapper.writeValueAsString(prdProductCategorySaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void shouldUpdatePrice () throws Exception {

        MvcResult result = mockMvc.perform(
                patch(BASE_PATH + "?taxRate=1&categoryType=FOOD").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void findAllProductWithDetail () throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/details").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}