package com.group6.entities;

import com.group6.ErrorForm;

import java.sql.*;
import java.util.ArrayList;

public class Person implements IEntity {

    public int personID = -1;
    public String name;
    public Date birthDate;
    public String streetAddress;
    public int postcode;
    public String city;
    public String gender;

    public Person() {

    }

    public Person(int personID, String name, Date birthDate, String streetAddress, int postcode, String city, String gender) {
        this.personID = personID;
        this.name = name;
        this.birthDate = birthDate;
        this.streetAddress = streetAddress;
        this.postcode = postcode;
        this.city = city;
        this.gender = gender;
    }

    @Override
    public Boolean select(Connection connection) throws SQLException {
        if ( name == null || streetAddress == null )
            return false;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Boolean returnValue = false;

        try {
            statement = connection.prepareStatement("SELECT * FROM People WHERE Name LIKE ? AND StreetAddress LIKE ?");
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + streetAddress + "%");

            resultSet = statement.executeQuery();

            if ( resultSet.next() ) {
                this.set(resultSet);
                returnValue = true;
            }

            resultSet.close();
            statement.close();

            return returnValue;
        } finally {
            resultSet.close();
            statement.close();
        }
    }

    @Override
    public ArrayList<IEntity> selectAll(Connection connection) throws SQLException {
        if ( name == null || streetAddress == null )
            return null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ArrayList<IEntity> entities = new ArrayList<IEntity>();

            statement = connection.prepareStatement("SELECT * FROM People WHERE Name LIKE ? AND StreetAddress LIKE ? ORDER BY Name");
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + streetAddress + "%");

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Person person = new Person();
                person.set(resultSet);
                entities.add(person);
            }

            resultSet.close();
            statement.close();

            return entities;
        } finally {
            resultSet.close();
            statement.close();
        }
    }

    @Override
    public Boolean insert(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(Connection connection) throws SQLException {
        if ( postcode < 0 ) {
            new ErrorForm("Postcode cannot be negative");
            return false;
        } else if ( !gender.equals("M") || !gender.equals("F") ) {
            new ErrorForm("Gender must be M or F");
            return false;
        }

        PreparedStatement statement = null;
        Boolean returnValue = false;

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement("UPDATE People SET Name = ?, BirthDate = ?, StreetAddress = ?, Postcode = ?, City = ?, Gender = ? WHERE PersonID = ?");
            statement.setString(1, name);
            statement.setDate(2, birthDate);
            statement.setString(3, streetAddress);
            statement.setInt(4, postcode);
            statement.setString(5, city);
            statement.setString(6, gender);
            statement.setInt(7, personID);

            returnValue = statement.executeUpdate() > 0;

            if ( !returnValue ) {
                new ErrorForm("Could not update data");
                connection.rollback();
            } else {
                connection.commit();
            }

            statement.close();

            return returnValue;
        } finally {
            statement.close();
        }
    }

    @Override
    public Boolean delete(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void set(ResultSet resultSet) throws SQLException {
        personID = resultSet.getInt("PersonID");
        name = resultSet.getString("Name");
        birthDate = resultSet.getDate("BirthDate");
        streetAddress = resultSet.getString("StreetAddress");
        postcode = resultSet.getInt("Postcode");
        city = resultSet.getString("City");
        gender = resultSet.getString("Gender");
    }

    @Override
    public String toString() {
        return "" + personID + "\n" +
                "" + name + "\n" +
                "" + birthDate + "\n" +
                "" + streetAddress + "\n" +
                "" + postcode + "\n" +
                "" + city + "\n" +
                "" + gender + "\n";
    }

}
