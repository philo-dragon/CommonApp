package com.pfl.dao;

import androidx.room.*;
import com.pfl.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE firstName == :name")
    List<User> loadAllUsersByFirstName(String name);

    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
    List<User> loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM user WHERE firstName LIKE :search " + "OR lastName LIKE :search")
    List<User> findUserWithName(String search);

}
