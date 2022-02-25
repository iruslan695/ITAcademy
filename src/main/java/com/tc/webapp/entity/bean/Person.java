package com.tc.webapp.entity.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Person implements Serializable {

    protected String name;
    protected String surname;
    protected String male;
    protected String[] interests;
    protected int age;
    protected String login;

    public Person() {
    }

    public Person(String name, String surname, String login, String male, String[] interests, int age) {
        this.name = name;
        this.surname = surname;
        this.male = male;
        this.login = login;
        this.interests = interests;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMale() {
        return male;
    }

    public String getLogin() {
        return login;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", male='" + male + '\'' +
                ", interests=" + Arrays.toString(interests) +
                ", age=" + age +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person user = (Person) o;
        return age == user.age && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(male, user.male);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, surname, male, age);
        result = 31 * result * Arrays.hashCode(interests);
        return result;
    }
}
