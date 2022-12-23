package com.maltseva.servicestation.project.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.TariffDTO;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.Tariff;
import com.maltseva.servicestation.project.service.userdetails.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TariffControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String CONTROLLER_PATH = "/tariff";

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
    public void getTariff() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/get")
                                .headers(generateHeaders())
                                .param("tariffId", "1")
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

        Tariff tariff = objectMapper.readValue(response, Tariff.class);
        System.out.println(tariff);
    }

    @Test
    public void listAllTariffByServiceStationId() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/listTariffByServiceStationId")
                                .headers(generateHeaders())
                                .param("ServiceStationId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<Tariff> tariffs = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(tariffs.size());
        //assertEquals(2, tariffs.size());

    }

    @Test
    public void createTariff() throws Exception {
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setPrice(333);
        tariffDTO.setServiceStationId(1L);
        tariffDTO.setStartDate(LocalDate.now());

        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(tariffDTO))
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

        Tariff tariff = objectMapper.readValue(response, Tariff.class);
        System.out.println(tariff);
    }

    @Test
    public void updateTariff() throws Exception {
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setPrice(444);
        tariffDTO.setServiceStationId(1L);


        String response = mockMvc.perform(
                        put(CONTROLLER_PATH + "/update")
                                .headers(generateHeaders())
                                .param("tariffId", "4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(tariffDTO))
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

        Tariff tariff = objectMapper.readValue(response, Tariff.class);
        System.out.println(tariff);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}