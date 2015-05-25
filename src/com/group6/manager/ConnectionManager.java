package com.group6.manager;

import com.group6.ErrorForm;
import com.group6.entities.IEntity;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionManager {

    // Driver to connect to the database. Lecture has it oracle/jdbc.dri...
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    // URL to the database (the one we use in class)
    static final String DB_URL = "jdbc:oracle:thin:@dwarf.cit.griffith.edu.au:1526:DBS";

    // Login details
    static final String USER = "s2841114"; //= "s2794576"; //= "s2841114";
    static final String PASS = "nether"; //= "asdf"; // = "nether";

    public static int get(IEntity entity, Class<? extends IEntity> clazz) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int found = 0;

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            if ( entity.select() != null )
                resultSet = statement.executeQuery(entity.select());

            if ( resultSet != null  ) {
                found = resultSet.next() ? 1 : 0;

                if ( found == 1 )
                    entity.set(resultSet);

                resultSet.close();
            }

            statement.close();
            connection.close();
        } catch(SQLException se) {
            new ErrorForm(se.getMessage());
            se.printStackTrace();
            found = -1;
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
            found = -1;
        } finally {
            try {
                if ( statement != null )
                    statement.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
                found = -1;
            }

            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
                found = -1;
            }
        }

        return found;
    }

    public static ArrayList<IEntity> getAll(IEntity entity, Class<? extends IEntity> clazz) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<IEntity> entities = new ArrayList<IEntity>();

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            if ( entity.selectAll() != null )
                resultSet = statement.executeQuery(entity.selectAll());

            if ( resultSet != null ) {
                while ( resultSet.next() ) {
                    IEntity newEntity = clazz.newInstance();
                    newEntity.set(resultSet);
                    entities.add(newEntity);
                }

                resultSet.close();
            }

            statement.close();
            connection.close();
        } catch(SQLException se) {
            new ErrorForm(se.getMessage());
            se.printStackTrace();
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
        } finally {
            try {
                if ( statement != null )
                    statement.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }

            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }
        }

        return entities;
    }

    public static String send(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        StringBuffer returnString = new StringBuffer("");

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cols = resultSetMetaData.getColumnCount();

            for ( int rows = 0; resultSet.next(); rows++ ){
                if ( rows >= 1 )
                    returnString.append("\n");

                for (int col = 1; col <= cols; col++ ) {
                    if ( col > 1 )
                        returnString.append("\t");

                    returnString.append(resultSet.getString(col));
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch(SQLException se) {
            new ErrorForm(se.getMessage());
            se.printStackTrace();
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
        } finally {
            try {
                if ( statement != null )
                    statement.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }

            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }
        }

        return returnString.toString();
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
