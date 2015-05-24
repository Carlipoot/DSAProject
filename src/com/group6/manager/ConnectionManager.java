package com.group6.manager;

import java.sql.*;

public class ConnectionManager {

    // Driver to connect to the database. Lecture has it oracle/jdbc.dri...
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    // URL to the database (the one we use in class)
    static final String DB_URL = "jdbc:oracle:thin:@dwarf.cit.griffith.edu.au:1526:DBS";

    // Login details
    static final String USER = "s2794576";
    static final String PASS = "asdf";

    public static ResultSet send(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            results = statement.executeQuery(query);

            statement.close();
            connection.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if ( statement != null )
                    statement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return results;
    }


    public static void test() {
        Connection connection = null;
        Statement statement = null;

        try{
            // Register driver
            System.out.println("Registering Driver...");
            Class.forName(JDBC_DRIVER);

            // Begin the connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create a statement
            System.out.println("Creating statement...\n");
            statement = connection.createStatement();

            // Make the query and send it to the database
            String query = "SELECT   E.ssn, E.name, E.bdate " +
                    "FROM     Employee E " +
                    "WHERE    E.ssn IN " +
                    "(SELECT  W.essn " +
                    "FROM     Works_on W " +
                    "GROUP BY W.essn " +
                    "HAVING   count(*) = 1)";

            ResultSet results = statement.executeQuery(query);

            // Begin displaying results
            System.out.println("Employees who work on only one Project:");

            while(results.next()){
                // Get all the data and print them to the console
                int ssn = results.getInt("ssn");
                String name = results.getString("name");
                Date bdate = results.getDate("bdate");

                System.out.println("SSN: " + ssn + ", Name: " + name + ", Birth Date: " + bdate);
            }

            // Close the statement and result set
            statement.close();
            results.close();

            // Close the connection
            System.out.println("Closing connection to database...");
            connection.close();

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();

        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();

        } finally {
            // If the statement didn't close then close it
            try {
                if ( statement != null )
                    statement.close();

            } catch (SQLException se) {
                se.printStackTrace();
            }

            // If the connection didn't close then close it
            try {
                if ( connection != null )
                    connection.close();

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Task completed.");
    }

}
