package com.pfl.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public Long id;
    public String firstName;
    public String lastName;
    private Integer age;

}