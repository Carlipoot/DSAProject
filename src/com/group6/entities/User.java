package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements IEntity {

    public String username;
    public String password;
    public String type;

    public User() {

    }

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    @Override
    public String select() {
        if ( username.isEmpty() || password.isEmpty() ) {
            //new ErrorForm("Wrong username or password");
            return null;
        } else {
            return "SELECT * FROM Users WHERE Username = '" + username.replace("'", "''") + "'";
        }
    }

    @Override
    public String selectAll() {
        return null;
    }

    @Override
    public String insert() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void set(ResultSet resultSet) {
        try {
            username = resultSet.getString("Username");
            password = resultSet.getString("Password");
            type = resultSet.getString("Type");
        } catch (SQLException se) {
            new ErrorForm("Could not get data");
            se.printStackTrace();
        }
    }
}
