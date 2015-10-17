package com.currconv.bean;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Login Bean to hold the information for the login screen
 * 
 * @author gauravD
 *
 */
public class LoginBean {
    /* Email address of the logged-in user */
    @NotEmpty(message = "Please enter registered email address")
    private String email;

    /* Password of the logged-in user */
    @NotEmpty(message = "Please enter a password")
    private String password;

    public String getPassword() {
	return this.password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
