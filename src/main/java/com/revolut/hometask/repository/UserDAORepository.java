package com.revolut.hometask.repository;


import com.revolut.hometask.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the User entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDAORepository extends JpaRepository<UserDAO, String> {

}

