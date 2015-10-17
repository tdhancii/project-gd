package com.currconv.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.currconv.bean.LoginBean;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserDTO;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.InvalidUserException;
import com.currconv.test.util.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    private static final String LOGIN = "login";
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_BEAN = "loginBean";
    private static final String LOGIN_MESSAGE_ATTRIBUTE = "loginMessage";
    private static final String REDIRECT_CONVERT = "redirect:/convert";
    private static final String REGISTRATION_MESSAGE = "User has been registered";
    private static final String INVALID_USER_EXCEPTION_MESSAGE = "Invalid User Exception";
    private static final String AUTHENTICATION_EXCEPTION_MESSAGE = "Authentication Failure";

    @Mock
    private UserDelegate userDelegateMock;

    @InjectMocks
    LoginController loginController = new LoginController();

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    BindingResult bindingResult;

    @Test
    public void displayLoginWithoutUserShouldReturnLogin() {
	when(request.getSession()).thenReturn(session);
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
	ModelAndView modelAndView = new ModelAndView();
	LoginBean loginBean = new LoginBean();

	String retValue = loginController.displayLogin(request, modelAndView, loginBean);

	assertNull(request.getAttribute(LOGIN_MESSAGE_ATTRIBUTE));
	assertTrue(modelAndView.getModel().keySet().contains(LOGIN_BEAN));
	assertEquals(retValue, LOGIN);
    }

    @Test
    public void displayLoginForNewRegisteredUserShouldReturnLogin() {
	when(request.getSession()).thenReturn(session);
	when(request.getAttribute(LOGIN_MESSAGE_ATTRIBUTE)).thenReturn("User has been registered");
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);

	ModelAndView modelAndView = new ModelAndView();
	LoginBean loginBean = new LoginBean();
	
	String retValue = loginController.displayLogin(request, modelAndView, loginBean);

	assertEquals(request.getAttribute(LOGIN_MESSAGE_ATTRIBUTE), REGISTRATION_MESSAGE);
	assertTrue(modelAndView.getModel().keySet().contains(LOGIN_BEAN));
	assertEquals(retValue, LOGIN);
    }

    @Test
    public void displayLoginWithUserShouldGotoConvertCurrency() {
	when(request.getSession()).thenReturn(session);
	when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(TestObjectBuilder.fixtureUserDTO());
	ModelAndView modelAndView = new ModelAndView();
	LoginBean loginBean = new LoginBean();

	String retValue = loginController.displayLogin(request, modelAndView, loginBean);

	assertEquals(retValue, REDIRECT_CONVERT);
    }

    @Test
    public void executeLoginWithBindingErrorsShouldStayInLoginPage() {

	when(bindingResult.hasErrors()).thenReturn(true);
	LoginBean loginBean = new LoginBean();
	String retValue = loginController.executeLogin(request, loginBean, bindingResult);

	assertEquals(retValue, LOGIN);
    }

    @Test
    public void executeLoginInvalidUserShouldStayinLoginPage() throws Throwable {
	LoginBean loginBean = TestObjectBuilder.fixtureLoginBean();

	when(bindingResult.hasErrors()).thenReturn(false);
	when(userDelegateMock.authenticateUser(loginBean.getEmail(), loginBean.getPassword()))
		.thenThrow(new InvalidUserException(INVALID_USER_EXCEPTION_MESSAGE));

	String retValue = loginController.executeLogin(request, loginBean, bindingResult);
	assertEquals(retValue, LOGIN);
    }

    @Test
    public void executeLoginAuthenticalFailureShouldStayinLoginPage() throws Throwable {
	LoginBean loginBean = TestObjectBuilder.fixtureLoginBean();

	when(bindingResult.hasErrors()).thenReturn(false);
	when(userDelegateMock.authenticateUser(loginBean.getEmail(), loginBean.getPassword()))
		.thenThrow(new AuthenticationFailureException(AUTHENTICATION_EXCEPTION_MESSAGE));

	String retValue = loginController.executeLogin(request, loginBean, bindingResult);
	assertEquals(retValue, LOGIN);
    }

    @Test
    public void executeLoginShouldPass() throws Throwable {
	LoginBean loginBean = TestObjectBuilder.fixtureLoginBean();
	UserDTO user = TestObjectBuilder.fixtureUserDTO();
	when(request.getSession()).thenReturn(session);
	when(bindingResult.hasErrors()).thenReturn(false);
	when(userDelegateMock.authenticateUser(loginBean.getEmail(), loginBean.getPassword())).thenReturn(user);

	String retValue = loginController.executeLogin(request, loginBean, bindingResult);

	assertEquals(retValue, REDIRECT_CONVERT);
	// Verify Results
	verify(userDelegateMock, times(1)).authenticateUser(anyString(), anyString());
    }

    @Test
    public void executeLogoutTest() throws Throwable {
	when(request.getSession()).thenReturn(session);
	LoginBean loginBean = TestObjectBuilder.fixtureLoginBean();

	String retValue = loginController.executeLogout(request, loginBean);
	assertEquals(retValue, LOGIN);
    }

}