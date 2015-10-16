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

@Controller
@RequestMapping(value = "/convert")
public class ConversionController {
    Logger logger = LoggerFactory.getLogger(ConversionController.class);

    @Autowired
    private CurrencyConversionDelegate conversionDelegate;

    @Autowired
    private UserDelegate userDelegate;

    @Autowired
    private MasterDataDelegate masterDataDelegate;

    private List<CurrencyDTO> currencyList;
    private static final String CURR_CONV_MESSAGE = "The value of {0} {1} is {2} {3} on {4}";

    /**
     * 
     * @param request
     * @param conversionBean
     * @param loginBean
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadConvertCurrency(HttpServletRequest request, ModelAndView modelAndView, @ModelAttribute("conversionBean") ConversionBean conversionBean) throws Exception {

	UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
	if (userDTO == null) {
	    logger.error("No user associated with the current session");
	    modelAndView = new ModelAndView("redirect:/login");
	    request.getSession().setAttribute("loginMessage", "No user associated with the current session! Please re-login");
	    return modelAndView;
	}
	modelAndView.addObject("conversionBean",conversionBean);
	modelAndView.addObject("userActivityList", userDTO.getUserActivityList());
	request.getSession().setAttribute("userNameData", userDTO.getFirstName() + " " + userDTO.getLastName());
	return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView convertCurrency(HttpServletRequest request, ModelAndView model,@Valid @ModelAttribute(value = "conversionBean") ConversionBean conversionBean, BindingResult bindingResult)
		    throws Exception {
	
	UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");	
	
	if (userDTO == null) {
	    logger.warn("No user associated with session.");
	    ModelAndView loginModel = new ModelAndView("redirect:/login");
	    request.getSession().setAttribute("loginMessage", "System Error occurred!");
	    return loginModel;
	}
	
	if (!bindingResult.hasErrors()) {
	    try {
		// Call the REST API through the Currency Conversion Service to get the currency rates.
		BigDecimal converstionRate = this.conversionDelegate.retrieveCurrencyRates(conversionBean.getFromCurrency(),
			conversionBean.getToCurrency(), conversionBean.getSourceAmount(),
			conversionBean.getConversionDate());
		
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
		request.setAttribute("errorMessage", e.getMessage());
	    } catch (RemoteException e) {
		logger.error("Remote exception from currency conversion service " + e.getMessage());
		request.setAttribute("errorMessage", e.getMessage());
	    }
	}
	model.addObject("userActivityList", userDTO.getUserActivityList());
	return model;
    }
    
    @ModelAttribute("currencyList")
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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	dateFormat.setLenient(false);

	binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
    }

    private String formatDate(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
	return format.format(date);
    }

    private UserActivityDTO constructUserActivityDTO(String emailID, BigDecimal convertedAmount,
	    ConversionBean conversionBean) {
	return new UserActivityDTO(emailID, conversionBean.getFromCurrency(), conversionBean.getToCurrency(),
		conversionBean.getSourceAmount(), convertedAmount, conversionBean.getConversionDate(),
		new Timestamp(Calendar.getInstance().getTime().getTime()));
    }

}
