package com.group6.entities;

import java.sql.Date;

public class Arrest {

    public int officerID;
    public int personID;
    public int postcode;
    public Date date;
    public String evidence;

    public Arrest() {

    }

    public Arrest(int officerID, int personID, int postcode, Date date, String evidence) {
        this.officerID = officerID;
        this.personID = personID;
        this.postcode = postcode;
        this.date = date;
        this.evidence = evidence;
    }

}
