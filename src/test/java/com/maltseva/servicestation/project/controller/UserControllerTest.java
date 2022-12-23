package com.maltseva.servicestation.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    private PositionControllerTest positionControllerTest;

    public void setPositionControllerTest(PositionControllerTest positionControllerTest) {
        this.positionControllerTest = positionControllerTest;
    }

    private static final String CONTROLLER_PATH = "/user";

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
    public void getUser() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/get")
                                .headers(generateHeaders())
                                .param("userId", "1")
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

        User user = objectMapper.readValue(response, User.class);
        System.out.println(user);
    }

    @Test
    public void getUserOwnerCarDTO() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/getUserOwnerCarDTO")
                                .headers(generateHeaders())
                                .param("userId", "12")
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

        UserOwnerCarDTO userOwnerCarDTO = objectMapper.readValue(response, UserOwnerCarDTO.class);
        System.out.println(userOwnerCarDTO);
    }

    @Test
    public void getUserEmployeeDTO() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/getUserEmployeeDTO")
                                .headers(generateHeaders())
                                .param("userId", "2")
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

        UserEmployeeDTO userEmployeeDTO = objectMapper.readValue(response, UserEmployeeDTO.class);
        System.out.println(userEmployeeDTO);
    }


    @Test
    public void listAllUser() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/list")
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

        List<User> users = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(users.size());

    }

    @Test
    public void createUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Учулен");
        userDTO.setLogin("Test");
        userDTO.setPassword("123");
        userDTO.setFirstName("Толя");
        userDTO.setLastName("Тестов");
        userDTO.setPhone("12345");
        userDTO.setDateBirth(LocalDate.now());
        userDTO.setBackUpEmail("email@mail.ru");

        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/add")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userDTO))
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

        User user = objectMapper.readValue(response, User.class);
        System.out.println(user);
    }

    @Test
    public void createUserEmployeer() throws Exception {
        UserEmployeeDTO userEmployeeDTO = new UserEmployeeDTO();
        userEmployeeDTO.setAddress("Учулен");
        userEmployeeDTO.setLogin("Test5");
        userEmployeeDTO.setPassword("123");
        userEmployeeDTO.setFirstName("Толя");
        userEmployeeDTO.setLastName("Тестов");
        userEmployeeDTO.setPhone("12345");
        userEmployeeDTO.setDateBirth(LocalDate.now());
        userEmployeeDTO.setBackUpEmail("email@mail.ru");

        userEmployeeDTO.setPositionID(1L);
        userEmployeeDTO.setEmployeeChiefID(3L);
        RoleDTO role = new RoleDTO();
        role.setId(2L);
        userEmployeeDTO.setRole(role);

        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/addEmployee")
                                .headers(generateHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userEmployeeDTO))
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

        User user = objectMapper.readValue(response, User.class);
        System.out.println(user);
    }

    @Test
    public void updateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Учулен");
        userDTO.setLogin("Test UPP");
        userDTO.setPassword("123");
        userDTO.setFirstName("Толя");
        userDTO.setLastName("Тестов");
        userDTO.setPhone("12345");
        userDTO.setDateBirth(LocalDate.now());
        userDTO.setBackUpEmail("email@mail.ru");


        String response = mockMvc.perform(
                        put(CONTROLLER_PATH + "/updateUser")
                                .headers(generateHeaders())
                                .param("userId", "9")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userDTO))
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

        User user = objectMapper.readValue(response, User.class);
        System.out.println(user);
    }

    @Test
    public void deleteUser() throws Exception {
        String response = mockMvc.perform(
                        delete(CONTROLLER_PATH + "/delete")
                                .param("userId", "44")
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

    @Test
    public void allEmployeeByChiefId() throws Exception {
        String result = mockMvc.perform(
                        get(CONTROLLER_PATH + "/allEmployeeByChiefId")
                                .headers(generateHeaders())
                                .param("chiefId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<UserChiefDTO> users = mapper.readValue(result, new TypeReference<>() {
        });
        System.out.println(users.size());

    }

    @Test
    public void getCarUser() throws Exception {
        String response = mockMvc.perform(
                        get(CONTROLLER_PATH + "/getCarsUserById")
                                .headers(generateHeaders())
                                .param("userId", "12")
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

        List<CarDTO> cars = objectMapper.readValue(response, new TypeReference<>() {
        });
        System.out.println(cars.size());
    }

    @Test
    public void sendPasswordRecoveryEmail() throws Exception {
        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/remember-password")
                                .headers(generateHeaders())
                                .param("email", "malceva.em@mail.ru")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(response);

    }

    @Test
    public void changePassword() throws Exception {
        String response = mockMvc.perform(
                        post(CONTROLLER_PATH + "/change-password/1")
                                .headers(generateHeaders())
                                .param("password", "123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(response);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
