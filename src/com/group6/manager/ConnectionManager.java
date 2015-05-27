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

    public static int get(IEntity entity) {
        Connection connection = null;
        int found = 0;

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            found = entity.select(connection) ? 1 : 0;

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

    public static Boolean update(IEntity entity) {
        Connection connection = null;
        Boolean returnValue = false;

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            returnValue = entity.update(connection);

            connection.close();
        } catch(SQLException se) {
            if ( se instanceof SQLIntegrityConstraintViolationException ) {
                if ( se.getMessage().toLowerCase().contains("chief") )
                    new ErrorForm("ChiefID does not exist");
            } else
                new ErrorForm(se.getMessage());

            se.printStackTrace();
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
        } finally {
            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }
        }

        return returnValue;
    }

    public static Boolean insert(IEntity entity) {
        Connection connection = null;
        Boolean returnValue =  false;

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            returnValue = entity.insert(connection);

            connection.close();
        } catch(SQLException se) {
            if ( se instanceof SQLIntegrityConstraintViolationException ) {
                if ( se.getMessage().toLowerCase().contains("chief") ) {
                    new ErrorForm("ChiefID does not exist");
                    se.printStackTrace();
                } else if ( se.getMessage().toLowerCase().contains("buildings_phone") ) {
                    new ErrorForm("Phone Number is used by another building");
                    se.printStackTrace();
                } else {
                    new ErrorForm(se.getMessage());
                    se.printStackTrace();
                }
            }
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
        } finally {
            try {
                if ( connection != null )
                    connection.close();
            } catch (SQLException se) {
                new ErrorForm(se.getMessage());
                se.printStackTrace();
            }
        }

        return returnValue;
    }

    public static ArrayList<IEntity> getAll(IEntity entity) {
        Connection connection = null;
        ArrayList<IEntity> entities = new ArrayList<IEntity>();

        try{
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            entities = entity.selectAll(connection);

            connection.close();
        } catch(SQLException se) {
            new ErrorForm(se.getMessage());
            se.printStackTrace();
        } catch(Exception e) {
            new ErrorForm("Could not find JDBC driver:" + JDBC_DRIVER);
            e.printStackTrace();
        } finally {
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

}
