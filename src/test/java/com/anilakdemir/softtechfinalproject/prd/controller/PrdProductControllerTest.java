package com.anilakdemir.softtechfinalproject.prd.controller;

import com.anilakdemir.softtechfinalproject.BaseTest;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductSaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductUpdateRequestDto;
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
class PrdProductControllerTest extends BaseTest {

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

        PrdProductSaveRequestDto prdProductSaveRequestDto = new PrdProductSaveRequestDto();
        prdProductSaveRequestDto.setPrice(new BigDecimal(100));
        prdProductSaveRequestDto.setName("Car");
        prdProductSaveRequestDto.setCategoryType(PrdProductCategoryType.TECHNOLOGY);

        String content = objectMapper.writeValueAsString(prdProductSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void update () throws Exception {


        PrdProductUpdateRequestDto prdProductUpdateRequestDto = new PrdProductUpdateRequestDto();
        prdProductUpdateRequestDto.setId(1L);
        prdProductUpdateRequestDto.setPrice(new BigDecimal(200));
        prdProductUpdateRequestDto.setName("CROP");
        prdProductUpdateRequestDto.setCategoryType(PrdProductCategoryType.CLOTHES);


        String content = objectMapper.writeValueAsString(prdProductUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void deleteById () throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/2").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void updatePrice () throws Exception {


        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/price/1?price=12").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findAllByCategoryType () throws Exception {


        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/category?prdProductCategoryType=FOOD").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findAllByTotalPriceBetween () throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/products/filter/by/price?min=12&max=100").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}