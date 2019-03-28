package com.revolut.hometask.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revolut.hometask.HelloworldApplication;
import com.revolut.hometask.dao.UserDAO;
import com.revolut.hometask.dto.UserDTO;
import com.revolut.hometask.repository.UserDAORepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloworldApplication.class)
public class UserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";
    private static final String DOB_STR = "1990-01-01";

    private static final LocalDate DEFAULT_DOB = LocalDate.of(1990, Month.JANUARY, 1);

    private static final ObjectMapper mapper = createObjectMapper();

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private UserDAORepository userDAORepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;


    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserMockMvc;

    private UserDAO userDAO;

    private UserDTO userDTO;




    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource userResource = new UserResource(userDAORepository);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Update an entity for this test.
     *
     */
    public static UserDAO createEntity(EntityManager em) {
        UserDAO user = new UserDAO();
        user.setUsername(DEFAULT_USERNAME);
        user.setDateOfBirth(DEFAULT_DOB);

        return user;
    }

    @Before
    public void initTest() {

        userDAO = createEntity(em);

        userDTO = new UserDTO();
        userDTO.setDateOfBirth(DOB_STR);
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        int databaseSizeBeforeCreate = userDAORepository.findAll().size();

        // Create the User
        restUserMockMvc.perform(put("/hello/"+DEFAULT_USERNAME)
            .contentType(APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsBytes(userDTO)))
            .andExpect(status().isNoContent());

        // Validate the User in the database
        List<UserDAO> userList = userDAORepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        UserDAO testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUser.getDateOfBirth()).isEqualTo(DEFAULT_DOB);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
