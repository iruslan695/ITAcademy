package com.tc.webapp.entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class User {
	private String login;
	private String password;
	private String name;
	private String surname;
	private String male;
	private String[] interests;
	private int age;

	public User(String login, String password, String name, String surname, String male, String[] interests, int age) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.male = male;
		this.interests = interests;
		this.age = age;
	}

	public User(HttpServletRequest request, HttpServletResponse response) {
		this.login = request.getParameter("login");
		this.password = request.getParameter("password");
		this.name = request.getParameter("name");
		this.surname = request.getParameter("surname");
		this.male = request.getParameter("male");
		this.age = Integer.parseInt(request.getParameter("age"));
		this.interests = request.getParameterValues("interests");
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
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

	public String[] getInterests() {
		return interests;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String createInfoURL() {
		String url = "login=" + this.login + "&age=" + this.age + "&male=" + this.male + "&name=" + this.name
				+ "&surname=" + this.surname;
		return url;
	}
}
