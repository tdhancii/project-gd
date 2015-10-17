package com.currconv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.currconv.bean.LoginBean;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserDTO;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.InvalidUserException;
/**
 * This controller is responsible to serve the requests pertaining to User Login
 * @author gauravD
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDelegate userDelegate;
    
    private static final String LOGIN = "login";
    private static final String REQUEST_MAPPING_SLASH = "/"; 
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_BEAN = "loginBean";
    private static final String LOGIN_MESSAGE_ATTRIBUTE = "loginMessage";
    private static final String REDIRECT_CONVERT = "redirect:/convert";
    private static final String LOGOUT = "/logout";
    
    private static final String INVALID_USER_EXCEPTION = "INVALID_USER_EXCEPTION";
    private static final String AUTHENTICATION_FAILURE_EXCEPTION = "AUTHENTICATION_FAILURE_EXCEPTION";
    private static final String UNEXPECTED_SYSTEM_EXCEPTION_EXCEPTION = "UNEXPECTED_SYSTEM_EXCEPTION_EXCEPTION";
    private static final String LOGOUT_MESSAGE = "LOGOUT_MESSAGE";
    

    /**
     * This method is responsible to load the Login Page. 
     * It checks if there is a valid user in the session and either redirects to the Convert screen or
     * renders the Login Page (for no user in the session)
     * 
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = { REQUEST_MAPPING_SLASH, LOGIN }, method = RequestMethod.GET)
    public String displayLogin(HttpServletRequest request, ModelAndView model,
	    @ModelAttribute(LOGIN_BEAN) LoginBean loginBean) {

	UserDTO loggedInUserDTO = (UserDTO) request.getSession().getAttribute(USER_ATTRIBUTE);
	if (loggedInUserDTO == null) {
	    logger.info("No User associated with the session");
	    model.addObject(LOGIN_BEAN, loginBean);
	    request.setAttribute(LOGIN_MESSAGE_ATTRIBUTE, request.getSession().getAttribute(LOGIN_MESSAGE_ATTRIBUTE));
	    return LOGIN;
	}
	//This means there is an existing session with a valid user. Redirect to convert screen.
	logger.info("User associated with the session. Navigating to Currency Convert screen");
	return REDIRECT_CONVERT;
    }

    /**
     * This method is invoked on the click of submit from the Login Page.
     * This method redirects the User to the Convert Currency screen for a valid login.
     * In case there is a failure based on Invalid User credentials or Non existing user login or a system exception,
     * an appropriate message is displayed to the user and the user remains on the Login screen.
     * 
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String executeLogin(HttpServletRequest request, @Valid @ModelAttribute(LOGIN_BEAN) LoginBean loginBean,
	    BindingResult bindingResult) {

	if (!bindingResult.hasErrors()) {
	    try {
		UserDTO userDTO = userDelegate.authenticateUser(loginBean.getEmail(), loginBean.getPassword());
		request.getSession().setAttribute(USER_ATTRIBUTE, userDTO);
		request.getSession().removeAttribute(LOGIN_MESSAGE_ATTRIBUTE);
		logger.info("Successful login ");
		return REDIRECT_CONVERT;
	    } catch (InvalidUserException e) {
		logger.error("InvalidUserException occurred while executing login ->" + e.getMessage());
		request.setAttribute(LOGIN_MESSAGE_ATTRIBUTE, INVALID_USER_EXCEPTION);
		return LOGIN;
	    } catch (AuthenticationFailureException e) {
		logger.error("Authentication Failure Exception occurred while executing login -> " + e.getMessage());
		request.setAttribute(LOGIN_MESSAGE_ATTRIBUTE, AUTHENTICATION_FAILURE_EXCEPTION);
		return LOGIN;
	    } catch (Exception e) {
		logger.error("System Exception occurred while executing login -> " + e.getMessage());
		request.setAttribute(LOGIN_MESSAGE_ATTRIBUTE, UNEXPECTED_SYSTEM_EXCEPTION_EXCEPTION);
		return LOGIN;
	    }
	}
	return LOGIN;
    }

    /**
     * This method handles the logout feature invoked on the click of Logout in the Convert Currency screen.
     * The user is navigated to the Login Page with an appropriate user message.
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = LOGOUT)
    public String executeLogout(HttpServletRequest request, @ModelAttribute(LOGIN_BEAN) LoginBean loginBean) {
	request.getSession().removeAttribute(USER_ATTRIBUTE);
	request.getSession().removeAttribute("userNameData");
	request.setAttribute(LOGIN_MESSAGE_ATTRIBUTE, LOGOUT_MESSAGE);
	logger.info("User has logged out successfully");
	return LOGIN;
    }
}