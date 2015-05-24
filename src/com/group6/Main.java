package com.group6;

import com.group6.entities.IEntity;
import com.group6.entities.Person;
import com.group6.manager.ConnectionManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DSAForm form = new DSAForm();

        Person person = (Person)ConnectionManager.get(Person.class);
        System.out.println(person);

        ArrayList<IEntity> entities = ConnectionManager.getAll(Person.class);
        for ( IEntity entity : entities )
            System.out.println((Person)entity);
    }

}
