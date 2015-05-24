package com.group6.entities;

import java.sql.Date;

public class Offence {

    public int offenceID;
    public int personID;
    public Date date;
    public int postcode;
    public String description;

    public Offence() {

    }

    public Offence(int offenceID, int personID, Date date, int postcode, String description) {
        this.offenceID = offenceID;
        this.personID = personID;
        this.date = date;
        this.postcode = postcode;
        this.description = description;
    }

}
