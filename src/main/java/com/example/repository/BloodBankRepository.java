package com.example.repository;

import com.example.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BloodBankRepository extends JpaRepository<UserModel, Long> {

//    @Query(value = "SELECT * FROM user_model WHERE username = :username and password= :password", nativeQuery = true)
//    UserModel checkForLogin(String username, String password);
//
    @Query(value = "SELECT * FROM user_model WHERE username = :username", nativeQuery = true)
    UserModel checkForSignup(String username);

    UserModel findByUsername(String username);

//    @Query("SELECT user FROM  user_model WHERE user.role = :role1 OR user.role = :role2 ")
//    List<UserModel> findAllByRoleInOrderByName(String role1, String role2);

}

