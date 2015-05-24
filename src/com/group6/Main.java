package com.group6;

import com.group6.entities.IEntity;
import com.group6.entities.Offence;
import com.group6.entities.Person;
import com.group6.manager.ConnectionManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DSAForm form = new DSAForm();

        Person person = (Person)ConnectionManager.get(Person.class);
        System.out.println(person);

        Offence offence = (Offence)ConnectionManager.get(Offence.class);
        System.out.println(offence);

        ArrayList<IEntity> people = ConnectionManager.getAll(Person.class);
        for ( IEntity entity : people )
            System.out.println(entity);

        ArrayList<IEntity> offences = ConnectionManager.getAll(Offence.class);
        for ( IEntity entity : offences )
            System.out.println(entity);
    }

}
