package com.currconv.dto;

/**
 * Data transfer object to hold Login information.
 * @author gauravD
 *
 */
public class LoginDTO {
    private String emailID;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String emailID, String password) {
	this.emailID = emailID;
	this.password = password;
    }

    public String getEmailID() {
	return emailID;
    }

    public void setEmailID(String emailID) {
	this.emailID = emailID;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
