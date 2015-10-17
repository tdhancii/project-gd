package com.currconv.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.currconv.bean.RegistrationBean;
import com.currconv.delegate.MasterDataDelegate;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserDTO;
import com.currconv.exception.EmailAlreadyRegisteredException;
import com.currconv.test.util.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private static final String REGISTRATION = "registration";
    private static final String EMAIL_ALREADY_REGISTERED = "Email is already Registered";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REGISTRATION_BEAN = "registrationBean";

    @Mock
    private UserDelegate userDelegateMock;

    @Mock
    private MasterDataDelegate masterDataDelegateMock;

    @InjectMocks
    RegistrationController registrationController = new RegistrationController();

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    BindingResult bindingResult;

    @Test
    public void displayRegistrationTest() {
	when(request.getSession()).thenReturn(session);
	RegistrationBean registrationBean = new RegistrationBean();
	ModelAndView modelAndView = registrationController.displayRegistration(session, registrationBean);
	assertTrue(modelAndView.getModel().keySet().contains(REGISTRATION_BEAN));
    }

    @Test
    public void registerUserWithBindingErrorsShouldStayInRegistrationPage() {
	when(request.getSession()).thenReturn(session);
	when(bindingResult.hasErrors()).thenReturn(true);
	RegistrationBean registrationBean = TestObjectBuilder.fixtureRegistrationBean();
	String retValue = registrationController.registerUser(request, registrationBean, bindingResult);
	assertEquals(retValue, REGISTRATION);
    }

    // TODO: @Test
    public void registerUserWithEmailAlreadyRegisteredExceptionShouldStayInRegistrationPage() throws Throwable {
	when(request.getSession()).thenReturn(session);
	when(bindingResult.hasErrors()).thenReturn(false);

	UserDTO user = TestObjectBuilder.fixtureUserDTO();

	doThrow(new EmailAlreadyRegisteredException(EMAIL_ALREADY_REGISTERED)).when(userDelegateMock)
		.registerNewUser(user);

	RegistrationBean registrationBean = TestObjectBuilder.fixtureRegistrationBean();
	String retValue = registrationController.registerUser(request, registrationBean, bindingResult);
	assertEquals(retValue, REGISTRATION);
    }

    @Test
    public void registerUserSuccessfulShouldGoToLoginPage() throws Throwable {
	when(request.getSession()).thenReturn(session);
	when(bindingResult.hasErrors()).thenReturn(false);

	RegistrationBean registrationBean = TestObjectBuilder.fixtureRegistrationBean();
	String retValue = registrationController.registerUser(request, registrationBean, bindingResult);
	assertEquals(retValue, REDIRECT_LOGIN);
    }
}