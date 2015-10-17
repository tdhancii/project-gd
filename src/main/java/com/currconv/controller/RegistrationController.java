package com.currconv.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.currconv.bean.RegistrationBean;
import com.currconv.delegate.MasterDataDelegate;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.CountryDTO;
import com.currconv.dto.UserDTO;
import com.currconv.exception.DataException;
import com.currconv.exception.EmailAlreadyRegisteredException;

/**
 * This controller serves requests pertaining to the Registration functionality
 * of the application
 * 
 * @author gauravD
 *
 */
@Controller
@RequestMapping(value = "/userRegister")
public class RegistrationController {
    /* Logger to print logging statements */
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserDelegate userDelegate;

    @Autowired
    MasterDataDelegate masterDelegate;

    private static final String REGISTRATION_MESSAGE = "{0} {1} was succesfully registered!";

    private static final String REGISTRATION_BEAN = "registrationBean";
    private static final String REGISTRATION = "registration";
    private static final String LOGIN_MESSAGE_ATTRIBUTE = "loginMessage";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String EMAIL_ALREADY_REGISTERED = "EMAIL_ALREADY_REGISTERED";
    private static final String COUNTRY_LIST = "countryList";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    

    /* List to hold Country values */
    private List<CountryDTO> countryList;

    /**
     * This method is responsible for displaying the User Registration screen.
     * 
     * @param session
     * @param registrationBean
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayRegistration(HttpSession session,
	    @ModelAttribute(REGISTRATION_BEAN) RegistrationBean registrationBean) {
	ModelAndView model = new ModelAndView(REGISTRATION);
	model.addObject(REGISTRATION_BEAN, registrationBean);

	session.removeAttribute(LOGIN_MESSAGE_ATTRIBUTE);
	return model;
    }

    /**
     * This method is responsible for registration of the user once all the
     * relevant details are entered in the User Registration screen. This method
     * will first check for BindingResult errors, if present, the user would be
     * navigated to Login screen with relevant message.
     * 
     * In case of no binding errors, the user details with an unique emailID
     * would be saved and navigated to the Login screen. In case the user-email
     * is already registered, the User will be prompted with a relevant message.
     * 
     * @param request
     * @param registrationBean
     * @param bindingResult
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request,
	    @Valid @ModelAttribute(REGISTRATION_BEAN) RegistrationBean registrationBean, BindingResult bindingResult) {
	if (!bindingResult.hasErrors()) {
	    try {
		userDelegate.registerNewUser(new UserDTO(registrationBean.getEmailID(), registrationBean.getPassword(),
			registrationBean.getFirstName(), registrationBean.getLastName(),
			registrationBean.getDateOfBirth(), registrationBean.getAddressLine1(),
			registrationBean.getAddressLine2(), registrationBean.getStreet(), registrationBean.getZip(),
			registrationBean.getCity(), registrationBean.getCountry()));

	    } catch (EmailAlreadyRegisteredException e) {
		logger.error("EmailAlreadyRegisteredException occurred while user registration ->" + e.getMessage());
		request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, EMAIL_ALREADY_REGISTERED);
		return REGISTRATION;
	    }
	    logger.info("User successfully registered");
	    String regMessage = MessageFormat.format(REGISTRATION_MESSAGE, registrationBean.getFirstName(),
		    registrationBean.getLastName());

	    request.getSession().setAttribute(LOGIN_MESSAGE_ATTRIBUTE, regMessage);
	    return REDIRECT_LOGIN;
	} else {
	    return REGISTRATION;
	}
    }

    /**
     * This method would enable populating the country values in the User
     * Registration screen.
     * 
     * @return
     */
    @ModelAttribute(COUNTRY_LIST)
    public List<CountryDTO> populateCountryList() {
	try {
	    if (countryList == null || countryList.isEmpty()) {
		this.countryList = masterDelegate.retrieveCountryList();
	    }
	} catch (DataException e) {
	    logger.error("Exception occurred while populating country list ->" + e.getMessage());
	}
	return countryList;
    }

    /**
     * Used to handle Date data types on the screen.
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	dateFormat.setLenient(false);
    }
}
