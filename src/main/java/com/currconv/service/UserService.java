/**
 *
 */
package com.currconv.service;

import java.util.List;

import com.currconv.dto.LoginDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.EmailAlreadyRegisteredException;
import com.currconv.exception.InvalidUserException;

/**
 *
 */
public interface UserService
{
   void registerNewUser(final UserDTO userDTO) throws EmailAlreadyRegisteredException;
   
   List<UserActivityDTO> refreshUserActivityList(final UserActivityDTO activityDTO) throws InvalidUserException;

   UserDTO authenticateUser(final LoginDTO loginDTO) throws InvalidUserException, AuthenticationFailureException;
   
}
