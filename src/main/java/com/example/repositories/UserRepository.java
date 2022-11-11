package com.example.repositories;

import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    User findById(int id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value="select user_name from user where user_id = ?1", nativeQuery = true)
    String findUserNameByUserId(int user_id);

    @Query(value="select email from user where user_id = ?1", nativeQuery = true)
    String findEmailByUserId(int user_id);

    @Query(value = "select * from user u join user_roles ur on u.user_id = ur.user_id where ur.role_id=?1", nativeQuery = true)
    List<User> getAllUserByRoleId(@RequestBody int roleId);


    @Query(value="select address from user where user_id=?1", nativeQuery = true)
    String getAddressFromUserByUserId(int id);

    @Query(value="select role_id from user_roles ur " +
            "join user u on ur.user_id = u.user_id " +
            "where u.user_id = ?1", nativeQuery = true)
    int findUserRoleIdByUserId(int id);

    @Query(value="select password from user where user_id=?1", nativeQuery = true)
    String findPasswordByUserId(int userId);
}
