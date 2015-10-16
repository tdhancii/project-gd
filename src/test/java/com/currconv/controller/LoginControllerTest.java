package com.currconv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
public class LoginControllerTest {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Mock
    private UserDelegate userDelegate;
    
    //@Before
    public void setup(){
	
    }

    /**
     * 1. Check if the existence of a User Session and navigate to Convert screen.
     * 2. Check if there is no User Session and navigate to Login Screen for first time.
     * 3. Check if there is no User Session and User has Registered to display the message.
     */
    @RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
    public String displayLogin(HttpServletRequest request, ModelAndView model,
	    @ModelAttribute("loginBean") LoginBean loginBean) {

	UserDTO loggedInUserDTO = (UserDTO) request.getSession().getAttribute("user");
	if (loggedInUserDTO == null) {
	    model.addObject("loginBean", loginBean);
	    request.setAttribute("loginMessage", request.getSession().getAttribute("registrationMessage"));
	    return "login";
	}
	return "redirect:/convert";
    }

    /**
     * 1. Binding result errors - navigation back to Login or to Convert
     * 2. Invalid User Exception testing - navigation and login message should be tested
     * 3. Authentication Failure Exception testing - navigation and login message should be tested
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String executeLogin(HttpServletRequest request, @Valid @ModelAttribute("loginBean") LoginBean loginBean,
	    BindingResult bindingResult) {

	if (!bindingResult.hasErrors()) {
	    try {
		UserDTO userDTO = userDelegate.authenticateUser(loginBean.getEmail(), loginBean.getPassword());
		request.getSession().setAttribute("user", userDTO);
		logger.info("Successful login ");
		return "redirect:/convert";
	    } catch (InvalidUserException e) {
		request.setAttribute("loginMessage", "User does not exist. Kindly register to access the system.");
		return "login";
	    } catch (AuthenticationFailureException e) {
		logger.error("Authentication Failure Exception occurred while executing login -> " + e.getMessage());
		request.setAttribute("loginMessage", "Authentication Failure! ");
		return "login";
	    } catch (Exception e) {
		logger.error("System Exception occurred while executing login -> " + e.getMessage());
		request.setAttribute("loginMessage", "System Error occurred! Please visit us later! ");
		return "login";
	    }
	}
	return "login";
    }

    /**
     * 
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = "/logout")
    public String executeLogout(HttpServletRequest request, @ModelAttribute("loginBean") LoginBean loginBean) {
	request.getSession().removeAttribute("user");
	request.getSession().removeAttribute("userNameData");
	request.setAttribute("loginMessage", "You have successfully logged out");
	logger.info("User has logged out successfully");
	return "login";
    }
}