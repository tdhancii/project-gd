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

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDelegate userDelegate;

    /**
     * 
     * @param request
     * @param response
     * @param loginBean
     * @return
     */
    @RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
    public String displayLogin(HttpServletRequest request, ModelAndView model,
	    @ModelAttribute("loginBean") LoginBean loginBean) {

	UserDTO loggedInUserDTO = (UserDTO) request.getSession().getAttribute("user");
	if (loggedInUserDTO == null) {
	    model.addObject("loginBean", loginBean);
	    request.setAttribute("loginMessage", request.getSession().getAttribute("loginMessage"));
	    return "login";
	}
	return "redirect:/convert";
    }

    /**
     * 
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
		request.getSession().removeAttribute("loginMessage");
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