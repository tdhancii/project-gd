package com.currconv.test.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.currconv.bean.LoginBean;
import com.currconv.bean.RegistrationBean;
import com.currconv.dto.LoginDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.entity.Address;
import com.currconv.entity.User;
import com.currconv.entity.UserActivity;

public class TestObjectBuilder {

    public static UserDTO fixtureUserDTO() {
	UserDTO user = new UserDTO("emailID", "password", "firstName", "lastName", null, "addressLine1", "addressLine2",
		"street", "zip", "city", "country");
	return user;
    }

    public static UserActivityDTO fixtureUserActivityDTO() {
	UserActivityDTO userActivityDTO = new UserActivityDTO("emailID", "fromCurr", "toCurr", new BigDecimal(0),
		new BigDecimal(0), new Date(), new Timestamp(new Date().getTime()));
	return userActivityDTO;
    }

    public static LoginDTO fixtureLoginDTO() {
	LoginDTO loginDTO = new LoginDTO("emailID", "password");
	return loginDTO;
    }

    public static LoginBean fixtureLoginBean() {
	LoginBean loginBean = new LoginBean();
	loginBean.setEmail("user@email.com");
	loginBean.setPassword("wrong123456");
	return loginBean;
    }

    public static RegistrationBean fixtureRegistrationBean() {
	RegistrationBean registrationBean = new RegistrationBean();
	registrationBean.setEmailID("emailID");
	registrationBean.setPassword("password");
	registrationBean.setFirstName("firstName");
	registrationBean.setLastName("lastName");
	registrationBean.setAddressLine1("addressLine1");
	registrationBean.setAddressLine2("addressLine2");
	registrationBean.setCity("city");
	registrationBean.setStreet("street");
	registrationBean.setZip("zip");
	registrationBean.setCountry("country");
	return registrationBean;
    }

    public static User fixtureUserModel() {
	User user = new User();
	user.setEmailID("userfound@emailID.com");
	user.setPassword("password");
	user.setFirstName("firstName");
	user.setLastName("lastName");

	Address address = new Address();
	user.setAddress(address);

	address.setAddressLine1("addressLine1");
	address.setAddressLine2("addressLine2");
	address.setCity("city");
	address.setStreet("street");
	address.setZip("zip");
	address.setCountry("country");
	return user;
    }

    public static UserActivity fixtureUserActivityModel() {
	UserActivity userActivity = new UserActivity();
	userActivity.setUser(fixtureUserModel());
	userActivity.setFromCurrency("fromcurrency");
	userActivity.setToCurrency("tocurrency");
	userActivity.setConvertedAmount(new BigDecimal(0));
	userActivity.setConvertedDate(new Date());
	userActivity.setCreatedDate(new Timestamp(new Date().getTime()));

	return userActivity;
    }

    public static UserActivityDTO buildUserActivityDTODummy() {
	return new UserActivityDTO(System.currentTimeMillis() + "abc@xyz.com", "USD", "NZD", new BigDecimal(1),
		new BigDecimal(5), new Date(), new Timestamp(new Date().getTime()));
    }

    /*
     * public static UserDTO buildUserDTOSpecific(String email, String paswd,
     * String firstName, String lastName, Date dob, String add1, String add2,
     * String street, String zip, String city, String country){ return new
     * UserDTO(email, paswd, firstName, lastName, dob, add1, add2, street, zip,
     * city, country); }
     */

}
