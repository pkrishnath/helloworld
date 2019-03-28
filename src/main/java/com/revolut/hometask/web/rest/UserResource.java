package com.revolut.hometask.web.rest;

import com.revolut.hometask.dao.UserDAO;
import com.revolut.hometask.dto.UserDTO;
import com.revolut.hometask.repository.UserDAORepository;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


/**
 * REST controller for managing User.
 */
@RestController
@RequestMapping("/hello")
@Log
public class UserResource {


    private final UserDAORepository userDAORepository;

    public UserResource(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

    /**
     * PUT  /:userName : PUT the "userName" user.
     *
     * @param userName the id of the user to retrieve
     * */

    @PutMapping("/{userName:[a-zA-Z]+}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable ("userName") @Size(max = 10) String userName, @Valid  @RequestBody UserDTO userDTO) throws URISyntaxException {

        log.info("Updating Birthday info for given user : {userName}");
        
        Optional<UserDAO> result = userDAORepository.findById(userName);
        
        UserDAO userDAO = result.isPresent() ? result.get(): new UserDAO();

        userDAO.setUsername(userName);

        userDAO.setDateOfBirth(LocalDate.parse(userDTO.getDateOfBirth()));

        userDAORepository.save(userDAO);
    }

    /**
     * GET  /:userName : get the  user.
     *
     * @param userName the id of the user to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{userName:[a-zA-Z]+}")
    public ResponseEntity<UserDTO> getBirthday(@PathVariable  @NotBlank @Pattern(regexp = "^[a-zA-Z]+$") String userName) throws URISyntaxException  {

        log.info("Getting Birthday info for given user : {userName}");

        UserDTO userDTO = null;

        String birthdayMsg = null;

        Optional<UserDAO> userDAO = userDAORepository.findById(userName);


        if(userDAO.isPresent()){

            LocalDate today = LocalDate.now();
            LocalDate birthday =  userDAO.get().getDateOfBirth();
            LocalDate nextBDay = birthday.withYear(today.getYear());

            long days = ChronoUnit.DAYS.between(today, nextBDay);

            if (days == 0){

                birthdayMsg = "Happy birthday!";

            } else{

                birthdayMsg = String.format("Your birthday is in %s days(s)", days);
            }

            String hello = String.format("Hello, %s!" , userDAO.get().getUsername());

            userDTO = new UserDTO();

            userDTO.setMessage(String.format("%s %s", hello , birthdayMsg));

        }

        Optional<UserDTO> user= Optional.ofNullable(userDTO);

        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}

