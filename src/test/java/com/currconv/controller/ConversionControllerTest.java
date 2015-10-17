package com.currconv.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.currconv.dto.UserDTO;
import com.currconv.exception.RemoteException;
import com.currconv.test.util.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ConversionControllerTest {

    private static final String LOGIN_REDIRECT = "redirect:/login";
    private static final String ERROR_MESSAGE = "Error";
    private static final String USER_ATTRIBUTE = "user";

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

    @InjectMocks
    private ConversionController conversionController = new ConversionController();

    @Test
    public void loadConvertCurrencyWithoutUserShouldRequestLogin() throws Exception {
	// Given the Scenario
	when(request.getSession()).thenReturn(session);
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
	ModelAndView model = new ModelAndView();
	ConversionBean conversionBean = new ConversionBean();

	// When
	ModelAndView modelAndView = conversionController.loadConvertCurrency(request, model, conversionBean);

	// then
	assertEquals(modelAndView.getViewName(), LOGIN_REDIRECT);
    }

    @Test
    public void loadConvertCurrencyWithUserinSessionShouldLoadPage() throws Exception {
	// Given the Scenario
	when(request.getSession()).thenReturn(session);
	UserDTO user = TestObjectBuilder.fixtureUserDTO();
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
	ModelAndView model = new ModelAndView();
	ConversionBean conversionBean = new ConversionBean();

	// When
	ModelAndView modelAndView = conversionController.loadConvertCurrency(request, model, conversionBean);

	// then
	assertTrue(modelAndView.getModel().keySet().contains("conversionBean"));
	assertTrue(modelAndView.getModel().keySet().contains("userActivityList"));

    }

    @Test
    public void convertCurrencyWithoutUserShouldRedirectToLogin() throws Exception {
	// Given the scenario
	when(request.getSession()).thenReturn(session);
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
	ModelAndView model = new ModelAndView();
	ConversionBean conversionBean = new ConversionBean();

	// when
	ModelAndView modelAndView = conversionController.convertCurrency(request, model, conversionBean, bindingResult);

	// then
	assertEquals(modelAndView.getViewName(), LOGIN_REDIRECT);
    }

    @Test
    public void convertCurrencyretrieveCurrencyFailureShouldFail() throws Throwable {
	// Given the Scenario
	when(request.getSession()).thenReturn(session);
	UserDTO user = TestObjectBuilder.fixtureUserDTO();

	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
	ModelAndView model = new ModelAndView();
	ConversionBean conversionBean = new ConversionBean();
	when(bindingResult.hasErrors()).thenReturn(false);

	when(conversionDelegate.retrieveCurrencyRates(anyString(), anyString(), any(Date.class)))
		.thenThrow(new RemoteException(ERROR_MESSAGE));

	// when
	conversionController.convertCurrency(request, model, conversionBean, bindingResult);

	// then
	verify(conversionDelegate, times(1)).retrieveCurrencyRates(anyString(), anyString(), any(Date.class));
    }

}
