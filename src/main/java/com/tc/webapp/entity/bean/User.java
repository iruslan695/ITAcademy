package com.tc.webapp.entity.bean;

import java.util.Arrays;
import java.util.Objects;

public class User extends Person {

    private int id;
    private String role;
    private int balance;

    public User() {

    }

    public User(int id, String role, int balance, String name, String surname, String male, String[] interests, int age, String login) {
        this.id = id;
        this.role = role;
        this.balance = balance;
        this.name = name;
        this.surname = surname;
        this.male = male;
        this.interests = interests;
        this.age = age;
        this.login = login;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public int getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
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
        User user = (User) o;
        return id == user.id && balance == user.balance && age == user.age && Objects.equals(role, user.role) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(male, user.male) && Arrays.equals(interests, user.interests) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, role, balance, name, surname, male, age, login);
        result = 31 * result + Arrays.hashCode(interests);
        return result;
    }
}
