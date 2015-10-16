package com.currconv.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.currconv.exception.DataException;

@Controller
@RequestMapping(value = "/convert")
public class ConversionControllerTest {
    Logger logger = LoggerFactory.getLogger(ConversionController.class);

    @Mock
    private CurrencyConversionDelegate conversionDelegateMock;

    @Mock
    private UserDelegate userDelegateMock;

    @Mock
    private MasterDataDelegate masterDataDelegateMock;

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
	    modelAndView.addObject("loginMessage", "User session ended! Please re-login");
	    return modelAndView;
	}
	modelAndView.addObject("conversionBean",conversionBean);
	modelAndView.addObject("userActivityList", userDTO.getUserActivityList());
	request.getSession().setAttribute("userNameData", userDTO.getFirstName() + " " + userDTO.getLastName());
	return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView convertCurrency(HttpServletRequest request, ModelAndView model,
	    @Valid @ModelAttribute(value = "conversionBean") ConversionBean conversionBean, BindingResult bindingResult)
		    throws Exception {
	
	UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");	
	
	if (userDTO == null) {
	    ModelAndView loginModel = new ModelAndView("login");
	    loginModel.addObject("message", "System Error occurred!");
	    return loginModel;
	}
	
	if (!bindingResult.hasErrors()) {
	    try {
		/*// Call the REST API through the Currency Conversion Service to get the currency rates.
		BigDecimal convertedAmount = this.conversionDelegateMock.retrieveCurrencyRates(conversionBean.getFromCurrency(),
			conversionBean.getToCurrency(), conversionBean.getSourceAmount(),
			conversionBean.getConversionDate());

		// Save the User Activity History Data
		userDTO.setUserActivityList(this.userDelegateMock.refreshUserActivity(
			constructUserActivityDTO(userDTO.getEmailID(), convertedAmount, conversionBean)));

		// Prepare message for user.
		String convertedMessage = MessageFormat.format(CURR_CONV_MESSAGE, conversionBean.getFromCurrency(),
			conversionBean.getSourceAmount(), conversionBean.getToCurrency(), convertedAmount,
			formatDate(conversionBean.getConversionDate()));
		model.addObject("convertedAmountMessage", convertedMessage);*/

	    } catch (Exception e) {
		logger.error("Exception occurred in convert currency -> " + e.getMessage());
		ModelAndView loginModel = new ModelAndView("login");
		loginModel.addObject("message", "System Error occurred!");
		return loginModel;
	    }
	}
	model.addObject("userActivityList", userDTO.getUserActivityList());
	return model;
    }
    
    @ModelAttribute("currencyList")
    public List<CurrencyDTO> populateCurrencyList() throws DataException {
	try {
	    if (currencyList == null || currencyList.isEmpty()) {
		this.currencyList = masterDataDelegateMock.retrieveCurrencyList();
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
