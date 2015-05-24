package com.group6.entities;

import java.sql.Date;

public class Person {

    public int personID;
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

}
