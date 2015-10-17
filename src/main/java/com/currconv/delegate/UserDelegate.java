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

/**
 * This method is responsible for invocation of the User Service for relevant User related actions.
 * @author gauravD
 *
 */
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

	/**
	 * This method ensures authentication of the user logging in.
	 * The method would throw a relevant exception based on Invalid User or Incorrect Authentication scenarios
	 * @param username
	 * @param password
	 * @return
	 * @throws InvalidUserException
	 * @throws AuthenticationFailureException
	 */
	public UserDTO authenticateUser(String username, String password) throws InvalidUserException, AuthenticationFailureException {
		return userService.authenticateUser(new LoginDTO(username, password));
	}

	/**
	 * This method ensures that the User Activity list is refreshed everytime a new currency
	 * The method would throw a relevant exception based on Invalid User scenario
	 * rate is looked up.
	 * @param userActivityDTO
	 * @return
	 * @throws InvalidUserException
	 */
	public List<UserActivityDTO> refreshUserActivity(final UserActivityDTO userActivityDTO) throws InvalidUserException{
    		return userService.refreshUserActivityList(userActivityDTO); 
	}
	
	/**
	 * This method is responsible for calling the service to register a new user.
	 * The method would throw a relevant exception based on if User is already registered
	 * @param userDTO
	 * @throws EmailAlreadyRegisteredException
	 */
	public void registerNewUser(final UserDTO userDTO) throws EmailAlreadyRegisteredException{
		 this.userService.registerNewUser(userDTO);
	}

}
