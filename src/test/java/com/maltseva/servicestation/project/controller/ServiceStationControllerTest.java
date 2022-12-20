package com.maltseva.servicestation.project.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.ServiceStationDTO;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.ServiceStation;
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
public class ServiceStationControllerTest {
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
    public void listAllServiceStation() throws Exception {
        String result = mockMvc.perform(
                        get("/ServiceStation/list")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<ServiceStation> serviceStations = mapper.readValue(result, new TypeReference<List<ServiceStation>>() {
        });
        System.out.println(serviceStations.size());

        assertEquals(10, serviceStations.size());

    }

    @Test
    public void createServiceStation() throws Exception {
        ServiceStationDTO serviceStationDTO = new ServiceStationDTO();
        serviceStationDTO.setName("TestAdd STO");
        serviceStationDTO.setAddress("Perm");
        serviceStationDTO.setPhone("8-888-88");

        String response = mockMvc.perform(
                        post("/ServiceStation/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceStationDTO))
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

        ServiceStation serviceStation = objectMapper.readValue(response, ServiceStation.class);
        System.out.println(serviceStation);
    }

    @Test
    public void updateServiceStation() throws Exception {
        ServiceStationDTO serviceStationDTO = new ServiceStationDTO();
        serviceStationDTO.setName("TestAdd STO UP");
        serviceStationDTO.setAddress("Perm UP");
        serviceStationDTO.setPhone("8-888-88 UP");

        String response = mockMvc.perform(
                        put("/ServiceStation/update")
                                .headers(generateHeaders())
                                .param("ServiceStationId", "12")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(serviceStationDTO))
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

        ServiceStation serviceStation = objectMapper.readValue(response, ServiceStation.class);
        System.out.println(serviceStation);
    }

    @Test
    public void deleteServiceStation() throws Exception {
        String response = mockMvc.perform(
                        delete("/ServiceStation/delete")
                                .param("ServiceStationId", "12")
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
