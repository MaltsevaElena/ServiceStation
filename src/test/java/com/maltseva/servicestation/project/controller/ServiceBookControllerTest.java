package com.maltseva.servicestation.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.ServiceBookDTO;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.ServiceBook;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ServiceBookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String CONTROLLER_PATH = "/serviceBook";

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
    public void getServiceBook() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/get")
                                .headers(generateHeaders())
                                .param("serviceBookId", "1")
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

        ServiceBook serviceBook = objectMapper.readValue(response, ServiceBook.class);
        System.out.println(serviceBook);
    }

    @Test
    public void listServiceBookByCar() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/listServiceBookByCar")
                                .headers(generateHeaders())
                                .param("carId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<ServiceBook> serviceBooks = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(serviceBooks.size());

    }

    @Test
    public void createServiceBook() throws Exception {
        ServiceBookDTO serviceBookDTO = new ServiceBookDTO();
        serviceBookDTO.setAmountSparePart(1);
        serviceBookDTO.setCodeSparePart("Нету");
        serviceBookDTO.setMileageCar(1000);
        serviceBookDTO.setRepairDate(LocalDate.now());
        serviceBookDTO.setNameSparePart("Лампочка");
        serviceBookDTO.setUnitSparePart(Unit.PIECES);
        serviceBookDTO.setCarId(1L);

        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceBookDTO))
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

        ServiceBook serviceBook = objectMapper.readValue(response, ServiceBook.class);
        System.out.println(serviceBook);
    }

    @Test
    public void updateServiceStation() throws Exception {
        ServiceBookDTO serviceBookDTO = new ServiceBookDTO();
        serviceBookDTO.setAmountSparePart(2);
        serviceBookDTO.setCodeSparePart("Нету");
        serviceBookDTO.setMileageCar(1500);
        serviceBookDTO.setRepairDate(LocalDate.now());
        serviceBookDTO.setNameSparePart("Лампочка UP");
        serviceBookDTO.setUnitSparePart(Unit.PIECES);
        serviceBookDTO.setCarId(1L);


        String response = mockMvc.perform(
                        put(CONTROLLER_PATH + "/update")
                                .headers(generateHeaders())
                                .param("serviceBookId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceBookDTO))
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

        ServiceBook serviceBook = objectMapper.readValue(response, ServiceBook.class);
        System.out.println(serviceBook);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
