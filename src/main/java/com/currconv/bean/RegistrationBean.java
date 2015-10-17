package com.currconv.bean;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Bean for the holding values of User Registration screen
 * 
 * @author gauravD
 *
 */
public class RegistrationBean {
    /* Email Address of the user */
    @NotEmpty(message = "Email Address is mandatory")
    @Email(message = "Please enter a valid email address")
    private String emailID;

    /* Password of the user */
    @NotEmpty(message = "Password value is mandatory")
    @Size(min = 6, max = 15, message = "The Password field must be between {2} and {1} characters long")
    private String password;

    /* First Name of the user */
    @NotEmpty(message = "First Name is mandatory")
    private String firstName;

    /* Last Name of the user */
    @NotEmpty(message = "Last Name is mandatory")
    private String lastName;

    /* Date of Birth of the user */
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Future date of birth is not valid")
    private Date dateOfBirth;

    /* Address line 1 of the user */
    private String addressLine1;

    /* Address line 2 of the user */
    private String addressLine2;

    /* Street of the user */
    private String street;

    /* Zip of the user */
    private String zip;

    /* City of the user */
    private String city;

    /* Country of the user */
    private String country;

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmailID() {
	return emailID;
    }

    public void setEmailID(String emailID) {
	this.emailID = emailID;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    public String getAddressLine1() {
	return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
	return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }
}
