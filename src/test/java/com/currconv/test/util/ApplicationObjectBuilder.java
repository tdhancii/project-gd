package com.currconv.test.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;

public class ApplicationObjectBuilder {

    public static UserDTO buildUserDTODummy(){
	return new UserDTO("abc@xyz.com","123456","Gaurav","Dalal",new Date(),
		"Address Line1","Address Line2","Street","411212","Pune","IND");
    }
    
    public static UserActivityDTO buildUserActivityDTODummy(){
	return new UserActivityDTO(System.currentTimeMillis()+"abc@xyz.com", "USD", "NZD", new BigDecimal(1), new BigDecimal(5), new Date(), new Timestamp(new Date().getTime()));
    }
    
    
    
    /*
    public static UserDTO buildUserDTOSpecific(String email, String paswd, String firstName, String lastName, Date dob, String add1, String add2,
	    String street, String zip, String city, String country){
	return new UserDTO(email, paswd, firstName, lastName, dob, add1, add2,
		    street, zip, city, country);
    }*/
    
}
