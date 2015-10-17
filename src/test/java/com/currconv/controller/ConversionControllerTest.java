package com.currconv.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.currconv.bean.ConversionBean;
import com.currconv.delegate.CurrencyConversionDelegate;
import com.currconv.delegate.MasterDataDelegate;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.entity.UserActivity;
import com.currconv.exception.RemoteException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



@RunWith(MockitoJUnitRunner.class)
public class ConversionControllerTest {

	private static final String LOGIN_REDIRECT = "redirect:/login";
	
		
    @Mock
    private CurrencyConversionDelegate conversionDelegate;
    
    @Mock
    private UserDelegate userDelegate;

    @Mock
    private MasterDataDelegate masterDataDelegate;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpSession session;
    
    
    @Mock
    private BindingResult bindingResult;
    
   
    private UserDTO fixtureUserDTO() {
    	
    	UserDTO user = new UserDTO("emailID", "password", "firstName", "lastName", null, "addressLine1", "addressLine2", "street", "zip", "city", "country");
    	
    	return user;
    }
    
    @InjectMocks
    private ConversionController conversionController = new ConversionController();
    
    
    @Test
    public void loadConvertCurrencyWihtoutUserShouRequestLogin() throws Exception {
    	// Mock the Scenario    	
    	when(request.getSession()).thenReturn(session);
    	when(session.getAttribute("user")).thenReturn(null);
    	ModelAndView model = new ModelAndView();
    	ConversionBean conversionBean = new ConversionBean();
    	    	
    	// Invoker Controller
    	ModelAndView modelAndView = conversionController.loadConvertCurrency(request, model, conversionBean);
    	
    	
    	// Verify results
    	assertEquals(modelAndView.getViewName(),LOGIN_REDIRECT);
    }

    
    @Test
    public void loadConvertCurrencyWithUserinSessionShouldLoadPage() throws Exception {
    	// Mock the Scenario    	
    	when(request.getSession()).thenReturn(session);
    	UserDTO user = fixtureUserDTO();
    	when(session.getAttribute("user")).thenReturn(user);
    	ModelAndView model = new ModelAndView();
    	ConversionBean conversionBean = new ConversionBean();
    	    	
    	// Invoker Controller
    	ModelAndView modelAndView = conversionController.loadConvertCurrency(request, model, conversionBean);
    	
    	
    	// Verify results
    	assertTrue(modelAndView.getModel().keySet().contains("conversionBean"));   	
    	assertTrue(modelAndView.getModel().keySet().contains("userActivityList"));    	
    	
    }
    
    
    @Test
    public void convertCurrencyWithoutUserShouldRedirectToLogin() throws Exception {
    	// Mock the scenario
    	when(request.getSession()).thenReturn(session);
    	when(session.getAttribute("user")).thenReturn(null);
    	ModelAndView model = new ModelAndView();
    	ConversionBean conversionBean = new ConversionBean();
    	
    	// Invoke Controller
    	ModelAndView modelAndView = conversionController.convertCurrency(request, model, conversionBean, bindingResult);
    	
    	// Verify Results
    	assertEquals(modelAndView.getViewName(),LOGIN_REDIRECT);    	    	
    }
    
    @Test    
    public void convertCurrencyretrieveCurrencyFailureShouldFail() throws Throwable {
    	// Mock the Scenario
    	when(request.getSession()).thenReturn(session);
    	UserDTO user = fixtureUserDTO();
    	when(session.getAttribute("user")).thenReturn(user);
    	ModelAndView model = new ModelAndView();
    	ConversionBean conversionBean = new ConversionBean();
    	when(bindingResult.hasErrors()).thenReturn(false);
    	when(conversionDelegate.retrieveCurrencyRates(anyString(), anyString(), any(BigDecimal.class), any(Date.class))).thenThrow(new RemoteException("Error"));
    	
    	// Invoke Controller
    	conversionController.convertCurrency(request, model, conversionBean, bindingResult);
    	
    	// Verify Results
    	verify(conversionDelegate,times(1)).retrieveCurrencyRates(anyString(), anyString(), any(BigDecimal.class), any(Date.class));
    }

}
