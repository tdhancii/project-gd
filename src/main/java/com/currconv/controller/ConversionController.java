package com.currconv.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.currconv.bean.ConversionBean;
import com.currconv.delegate.CurrencyConversionDelegate;
import com.currconv.delegate.MasterDataDelegate;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.CurrencyDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.exception.CurrencyConversionNotSupported;
import com.currconv.exception.DataException;
import com.currconv.exception.RemoteException;
/**
 * The controller which serves requests pertaining to the Currency Conversion screen
 * @author dalalgau
 *
 */
@Controller
@RequestMapping(value = "/convert")
public class ConversionController {
    Logger logger = LoggerFactory.getLogger(ConversionController.class);

    /*Reference to the Currency Conversion Delegate*/
    @Autowired
    private CurrencyConversionDelegate conversionDelegate;

    /*Reference to the User Delegate*/
    @Autowired
    private UserDelegate userDelegate;

    /*Reference to the Master Delegate*/
    @Autowired
    private MasterDataDelegate masterDataDelegate;

    /*Reference to hold Lists of currencies fetched from the database*/
    private List<CurrencyDTO> currencyList;
    
    /*Public static String references used in the class*/
    private static final String CURR_CONV_MESSAGE = "The value of {0} {1} is {2} {3} on {4}";
    private static final String EXCEPTION_CODE_UNEXPECTED = "UNEXPECTED_SYSTEM_EXCEPTION_EXCEPTION";
    private static final String EXCEPTION_NO_VALID_USER_SESSION = "NO_USER_ASSOCIATED_SESSION_EXCEPTION";
    
    private static final String USER_ATTRIBUTE = "user";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String LOGIN_MESSAGE_ATTRIBUTE = "loginMessage";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String CONVERSION_BEAN = "conversionBean";
    private static final String USER_ACTIVITY_LIST_ATTRIBUTE = "userActivityList";
    private static final String USER_NAME_DATA = "userNameData";
    private static final String CURRENCY_LIST = "currencyList";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    
    
    /**
     * This method is responsible for loading the currency conversion screen.
     * This method first checks for presence of a valid user in the session and redirects the user
     * to either the Login screen (invalid users) or the Convert Currency screen.
     * 
     * @param request
     * @param conversionBean
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadConvertCurrency(HttpServletRequest request, ModelAndView modelAndView, @ModelAttribute(CONVERSION_BEAN) ConversionBean conversionBean) throws Exception {

	UserDTO userDTO = (UserDTO) request.getSession().getAttribute(USER_ATTRIBUTE);
	if (userDTO == null) {
	    logger.error(EXCEPTION_NO_VALID_USER_SESSION);
	    modelAndView = new ModelAndView(REDIRECT_LOGIN);
	    request.getSession().setAttribute(LOGIN_MESSAGE_ATTRIBUTE, EXCEPTION_NO_VALID_USER_SESSION);
	    return modelAndView;
	}
	modelAndView.addObject(CONVERSION_BEAN,conversionBean);
	modelAndView.addObject(USER_ACTIVITY_LIST_ATTRIBUTE, userDTO.getUserActivityList());
	request.getSession().setAttribute(USER_NAME_DATA, userDTO.getFirstName() + " " + userDTO.getLastName());
	return modelAndView;
    }


    /**
     * This method is invoked from the Currency Conversion screen.
     * 
     * This method is responsible for fetch the currency rates for the From Currency - To Currency combination for the provided date
     * and display to the user.
     * This method initially checks for a valid user in the session. In absence of a valid user, the application gets navigated
     * to the Login screen with an appropriate message.
     * If the user is valid, the method checks for Binding errors (which are displayed to the user on the same screen) and then invokes 
     * the Currency REST service via the Delegate.
     * This method also, converts the Amount provided by the user and displays the result to the user.
     * As a part of this action, the User Activity list also has to be updated.
     * 
     * CurrencyConversionException is thrown if the from-to currency conversion is not supported by the REST API.
     * RemoteException is handled to cater to any exception thrown by the service.
     * 
     * @param request
     * @param model
     * @param conversionBean
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView convertCurrency(HttpServletRequest request, ModelAndView model,@Valid @ModelAttribute(value = CONVERSION_BEAN) ConversionBean conversionBean, BindingResult bindingResult)
		    throws Exception {
	
	UserDTO userDTO = (UserDTO) request.getSession().getAttribute(USER_ATTRIBUTE);	
	
	if (userDTO == null) {
	    logger.warn("No user associated with session.");
	    ModelAndView loginModel = new ModelAndView(REDIRECT_LOGIN);
	    request.getSession().setAttribute(LOGIN_MESSAGE_ATTRIBUTE, EXCEPTION_CODE_UNEXPECTED);
	    return loginModel;
	}
	
	if (!bindingResult.hasErrors()) {
	    try {
		// Call the REST API through the Currency Conversion Service to get the currency rates.
		BigDecimal converstionRate = this.conversionDelegate.retrieveCurrencyRates(conversionBean.getFromCurrency(),
			conversionBean.getToCurrency(), conversionBean.getConversionDate());
		
		BigDecimal convertedAmount = conversionBean.getSourceAmount().multiply(converstionRate).setScale(2,RoundingMode.FLOOR);

		// Save the User Activity History Data
		userDTO.setUserActivityList(this.userDelegate.refreshUserActivity(
			constructUserActivityDTO(userDTO.getEmailID(), convertedAmount, conversionBean)));

		// Prepare message for user.
		String convertedMessage = MessageFormat.format(CURR_CONV_MESSAGE, conversionBean.getFromCurrency(),
			conversionBean.getSourceAmount(), conversionBean.getToCurrency(), convertedAmount,
			formatDate(conversionBean.getConversionDate()));
		model.addObject("convertedAmountMessage", convertedMessage);

	    } catch (CurrencyConversionNotSupported e) {
		logger.error("Currency conversion exception " + e.getMessage());
		request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
	    } catch (RemoteException e) {
		logger.error("Remote exception from currency conversion service " + e.getMessage());
		request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
	    }
	}
	model.addObject(USER_ACTIVITY_LIST_ATTRIBUTE, userDTO.getUserActivityList());
	return model;
    }
    
    /**
     * Maintain the currency list to be displayed to the end user.
     * @return
     * @throws DataException
     */
    @ModelAttribute(CURRENCY_LIST)
    public List<CurrencyDTO> populateCurrencyList() throws DataException {
	try {
	    if (currencyList == null || currencyList.isEmpty()) {
		this.currencyList = masterDataDelegate.retrieveCurrencyList();
	    }
	} catch (Exception e) {
	    logger.error("Exception occurred while populating currency list -> " + e.getMessage());
	}
	return currencyList;
    }
    
    /**
     * Used to handle Date and BigDecimal data types
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	dateFormat.setLenient(false);

	binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
    }

    /**
     * For Date formatting
     * @param date
     * @return
     */
    private String formatDate(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
	return format.format(date);
    }

    /**
     * Utility method to build the User Activity DTO object.
     * @param emailID
     * @param convertedAmount
     * @param conversionBean
     * @return
     */
    private UserActivityDTO constructUserActivityDTO(String emailID, BigDecimal convertedAmount,
	    ConversionBean conversionBean) {
	return new UserActivityDTO(emailID, conversionBean.getFromCurrency(), conversionBean.getToCurrency(),
		conversionBean.getSourceAmount(), convertedAmount, conversionBean.getConversionDate(),
		new Timestamp(Calendar.getInstance().getTime().getTime()));
    }

}
