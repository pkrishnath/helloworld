package com.revolut.hometask.web.rest;

import com.revolut.hometask.domian.User;
import com.revolut.hometask.repository.UserRepository;
import com.revolut.hometask.util.ResponseUtil;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing User.
 */
@RestController
@RequestMapping("/hello")
@Log
public class UserResource {


    private static final String ENTITY_NAME = "user";

    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PutMapping("/{username}")
    public void updateUser(@Valid @PathVariable String userName) throws URISyntaxException {
        Optional<User> user = userRepository.findById(userName);

        userRepository.save(user.get());
    }

    /**
     * GET  /:username : get the "id" user.
     *
     * @param username the id of the user to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user, or with status 404 (Not Found)
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        //log.debug("REST request to get User : {}", id);
        Optional<User> user = userRepository.findById(userName);
        return ResponseUtil.wrapOrNotFound(user);
    }

}

