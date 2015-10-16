package com.currconv.dto;

import java.util.Date;
import java.util.List;
/**
 * Data transfer object to hold User details.
 * @author gauravD
 *
 */
public class UserDTO {

    private String emailID;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String addressLine1;
    private String addressLine2;
    private String street;
    private String zip;
    private String city;
    private String country;

    private List<UserActivityDTO> userActivityList;

    public UserDTO(String emailID, String password, String firstName, String lastName, Date dateOfBirth,
	    String addressLine1, String addressLine2, String street, String zip, String city, String country) {
	this.emailID = emailID;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.dateOfBirth = dateOfBirth;
	this.addressLine1 = addressLine1;
	this.addressLine2 = addressLine2;
	this.street = street;
	this.zip = zip;
	this.city = city;
	this.country = country;

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

    public List<UserActivityDTO> getUserActivityList() {
	return userActivityList;
    }

    public void setUserActivityList(List<UserActivityDTO> userActivityList) {
	this.userActivityList = userActivityList;
    }
}
