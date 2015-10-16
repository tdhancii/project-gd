package com.currconv.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currconv.dto.LoginDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.EmailAlreadyRegisteredException;
import com.currconv.exception.InvalidUserException;
import com.currconv.service.UserService;

@Component
public class UserDelegate {
	
	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDTO authenticateUser(String username, String password) throws InvalidUserException, AuthenticationFailureException {
		return userService.authenticateUser(new LoginDTO(username, password));
	}

	public List<UserActivityDTO> refreshUserActivity(final UserActivityDTO userActivityDTO) throws InvalidUserException{
    	return userService.refreshUserActivityList(userActivityDTO); 
    }
	
	public void registerNewUser(final UserDTO userDTO) throws EmailAlreadyRegisteredException{
		 this.userService.registerNewUser(userDTO);
	}

}
