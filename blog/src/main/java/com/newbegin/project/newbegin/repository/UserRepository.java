package com.newbegin.project.newbegin.repository;

import com.newbegin.project.newbegin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);


    User findByEmail(String email);

    void deleteById(long id);

    @Query("select u.email from User u where u.email = :email")
    String getEmail(@Param("email") String email);

    @Query("select count(u) from User u")
    Integer countUsers();

    @Query("select count(u) from User u group by u.createdAt")
    List<Integer> countUsersByDate();

    List<User> findByUsernameContains(String username);

    @Query("select u.createdAt from User u group by u.createdAt")
    List<String> dateCreated();
}
