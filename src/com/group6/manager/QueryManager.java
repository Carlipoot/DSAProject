package com.group6.manager;

import com.group6.entities.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryManager {

    public static Arrest getArrest() {
        return new Arrest();
    }

    public static Offence getOffence() {
        return new Offence();
    }

    public static Person getPerson() {
        return new Person();
    }

    public static Prison getPrison() {
        return new Prison();
    }

    public static Station getStation() {
        return new Station();
    }

    public static User getUser() {
        return new User();
    }

    public static void test() {
        String query = "SELECT   E.ssn, E.name, E.bdate " +
                "FROM     Employee E " +
                "WHERE    E.ssn IN " +
                "(SELECT  W.essn " +
                "FROM     Works_on W " +
                "GROUP BY W.essn " +
                "HAVING   count(*) = 1)";

        try {
            ResultSet results = ConnectionManager.send(query);

            while(results.next()){
                // Get all the data and print them to the console
                int ssn = results.getInt("ssn");
                String name = results.getString("name");
                Date bdate = results.getDate("bdate");

                System.out.println("SSN: " + ssn + ", Name: " + name + ", Birth Date: " + bdate);
            }

            results.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
