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

@Controller
@RequestMapping(value = "/userRegister")
public class RegistrationControllerTest {
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserDelegate userDelegate;

    @Autowired
    MasterDataDelegate masterDelegate;

    private static final String REGISTRATION_MESSAGE = "{0} {1} was succesfully registered!";

    // private Map<String, String> countryListMap;
    private List<CountryDTO> countryList;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayRegistration(HttpSession session,@ModelAttribute("registrationBean") RegistrationBean registrationBean) {
	ModelAndView model = new ModelAndView("registration");
	model.addObject("registrationBean", registrationBean);
	
	session.removeAttribute("registrationMessage");
	return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request,
	    @Valid @ModelAttribute("registrationBean") RegistrationBean registrationBean, BindingResult bindingResult) {
	if (!bindingResult.hasErrors()) {
	    try {
		userDelegate.registerNewUser(new UserDTO(registrationBean.getEmailID(), registrationBean.getPassword(),
			registrationBean.getFirstName(), registrationBean.getLastName(),
			registrationBean.getDateOfBirth(), registrationBean.getAddressLine1(),
			registrationBean.getAddressLine2(), registrationBean.getStreet(), registrationBean.getZip(),
			registrationBean.getCity(), registrationBean.getCountry()));

	    } catch (EmailAlreadyRegisteredException e) {
		logger.error("EmailAlreadyRegisteredException occurred while user registration ->" + e.getMessage());
		request.setAttribute("errorMessage", "Email is already Registered");
		return "registration";
	    } catch (Exception e) {
		logger.error("System Exception occurred while user registration ->" + e.getMessage());
		request.setAttribute("errorMessage", "System error has occurred. Please visit us later");
		return "registration";
	    }

	    logger.info("User successfully registered");
	    String regMessage = MessageFormat.format(REGISTRATION_MESSAGE, registrationBean.getFirstName(),
		    registrationBean.getLastName());
	    request.getSession().setAttribute("registrationMessage", regMessage);
	    return "redirect:/login";
	} else {
	    return "registration";
	}
    }

    @ModelAttribute("countryList")
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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	dateFormat.setLenient(false);
    }
}
