package com.maltseva.servicestation.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.SparePartDTO;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.SparePart;
import com.maltseva.servicestation.project.model.Unit;
import com.maltseva.servicestation.project.service.userdetails.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SparePartControllerTest {
    private static final String CONTROLLER_PATH = "/sparePart";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String ROLE_USER_NAME = "elena_d";
    private String token;

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        token = generateToken(ROLE_USER_NAME);
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    private String generateToken(String userName) {
        return jwtTokenUtil.generateToken(customUserDetailsService.loadUserByUsername(userName));
    }

    @Test
    public void getSparePart() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/get")
                                .headers(generateHeaders())
                                .param("sparePartId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SparePart sparePart = objectMapper.readValue(response, SparePart.class);
        System.out.println(sparePart);
    }

    @Test
    public void listAllSparePartByWarehouseId() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/listSparePartByWarehouseId")
                                .headers(generateHeaders())
                                .param("WarehouseId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<SparePart> sparePartList = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(sparePartList.size());
        //assertEquals(7, sparePartList.size());
    }

    @Test
    public void createSparePart() throws Exception {
        SparePartDTO sparePartDTO = new SparePartDTO();
        sparePartDTO.setAmount(2);
        sparePartDTO.setName("ТестAdd Дворник");
        sparePartDTO.setUnit(Unit.PIECES);
        sparePartDTO.setPrice(777.8);
        sparePartDTO.setCode("yfu7r5stg");
        sparePartDTO.setWarehouseId(2L);


        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(sparePartDTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SparePart sparePart = objectMapper.readValue(response, SparePart.class);
        System.out.println(sparePart);
    }

    @Test
    public void updateSparePars() throws Exception {
        SparePartDTO sparePartDTO = new SparePartDTO();
        sparePartDTO.setAmount(3);
        sparePartDTO.setName("ТестAdd Дворник Upp");
        sparePartDTO.setUnit(Unit.PIECES);
        sparePartDTO.setPrice(777.8);
        sparePartDTO.setCode("yfu7r5stg");
        sparePartDTO.setWarehouseId(1L);

        String response = mockMvc.perform(
                        put(CONTROLLER_PATH + "/update")
                                .headers(generateHeaders())
                                .param("SparePartId", "12")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(sparePartDTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SparePart sparePart = objectMapper.readValue(response, SparePart.class);
        System.out.println(sparePart);
    }

    @Test
    public void deleteSparePart() throws Exception {
        String response = mockMvc.perform(
                        delete(CONTROLLER_PATH + "/delete")
                                .param("sparePartId", "13")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(response);
    }


    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
