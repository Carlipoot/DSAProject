package com.group6.entities;

import java.sql.ResultSet;

public interface IEntity {

    public String select();

    public String selectAll();

    public String insert();

    public String update();

    public String delete();

    public void set(ResultSet resultSet);

    public String toString();

}
