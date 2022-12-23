package com.maltseva.servicestation.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.ServiceDTO;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.Service;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String CONTROLLER_PATH = "/service";

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
    public void getService() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/get")
                                .headers(generateHeaders())
                                .param("serviceId", "1")
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

        Service service = objectMapper.readValue(response, Service.class);
        System.out.println(service);
    }

    @Test
    public void listAllServiceByServiceStationId() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/listAllServiceByServiceStationId")
                                .headers(generateHeaders())
                                .param("serviceStationId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<Service> service = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(service.size());
        assertEquals(11, service.size());

    }

    @Test
    public void createService() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServiceStationId(1L);
        serviceDTO.setCode("asdassf");
        serviceDTO.setName("Test service");
        serviceDTO.setRateHour(2.1);

        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceDTO))
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

        Service service = objectMapper.readValue(response, Service.class);
        System.out.println(service);
    }

    @Test
    public void updateService() throws Exception {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServiceStationId(1L);
        serviceDTO.setCode("test");
        serviceDTO.setName("Test service UP");
        serviceDTO.setRateHour(2.1);
        serviceDTO.setCreatedBy("Test");

        String response = mockMvc.perform(
                        put(CONTROLLER_PATH + "/update")
                                .headers(generateHeaders())
                                .param("serviceId", "11")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceDTO))
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

        Service service = objectMapper.readValue(response, Service.class);
        System.out.println(service);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

