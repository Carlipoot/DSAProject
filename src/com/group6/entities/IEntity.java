package com.group6.entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IEntity {

    public Boolean select(Connection connection)
            throws SQLException;

    public ArrayList<IEntity> selectAll(Connection connection)
            throws SQLException;

    public Boolean insert(Connection connection)
            throws SQLException;

    public Boolean update(Connection connection)
            throws SQLException;

    public Boolean delete(Connection connection)
            throws SQLException;

    public void set(ResultSet resultSet)
            throws SQLException;

    public String toString();

}
