package com.currconv.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserDTO;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.InvalidUserException;
import com.currconv.spring.BaseConfiguration;
import com.currconv.spring.WebConfiguration;
import com.currconv.test.util.ApplicationObjectBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes={WebConfiguration.class,BaseConfiguration.class})
@EnableAutoConfiguration(exclude = { VelocityAutoConfiguration.class })
public class LoginControllerTest {

    @Mock
    private UserDelegate userDelegateMock;

    private MockMvc mockMvc;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
    }

    @Test
    public void displayLoginNewUserSessionTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", null);
	mockMvc.perform((get("/")).session(session).requestAttr("loginMessage", "")).andExpect(status().isOk())
		.andExpect(view().name("login")).andExpect(forwardedUrl("login"));
    }

    @Test
    public void displayLoginExistingUserSessionTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/")).session(session)).andExpect(status().isOk())
		.andExpect(forwardedUrl("redirect:/convert"));
    }

    @Test
    public void executeLoginInvalidUserExceptionTest() throws Exception {
	when(userDelegateMock.authenticateUser("abc-1@xyz.com", "123456"))
		.thenThrow(new InvalidUserException("User does not exist"));

	mockMvc.perform(post("/login")).andExpect(forwardedUrl("login")).andExpect(status().isOk()).andExpect(
		request().attribute("loginMessage", "User does not exist. Kindly register to access the system."));
    }

    @Test
    public void executeLoginAuthenticationFailureExceptionTest() throws Exception {
	when(userDelegateMock.authenticateUser("abc-1@xyz.com", "123156"))
		.thenThrow(new AuthenticationFailureException("Authentication Failure Occurred"));

	mockMvc.perform(post("/login")).andExpect(forwardedUrl("login")).andExpect(status().isOk())
		.andExpect(request().attribute("loginMessage", "Authentication Failure!"));
    }

    @Test
    public void executeLoginSystemExceptionTest() throws Exception {
	when(userDelegateMock.authenticateUser("abc-1@xyz.com", "123156"))
		.thenThrow(new Exception("Authentication Failure Occurred"));

	mockMvc.perform(post("/login")).andExpect(forwardedUrl("login")).andExpect(status().isOk())
		.andExpect(request().attribute("loginMessage", "System Error occurred! Please visit us later!"));
    }

    @Test
    public void executeSuccessfulLoginTest() throws Exception {
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	when(userDelegateMock.authenticateUser("abc-1@xyz.com", "123456")).thenReturn(userDTO);
	MockHttpSession session = new MockHttpSession();

	mockMvc.perform(post("/login").session(session)).andExpect(status().isOk())
		.andExpect(request().sessionAttribute("user", userDTO)).andExpect(forwardedUrl("redirect:/convert"));
    }

    @Test
    public void executeSuccessfulLogoutTest() throws Exception {
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", userDTO);
	session.setAttribute("userNameData", userDTO.getFirstName());
	mockMvc.perform(post("/logout").session(session)).andExpect(status().isOk())
		.andExpect(request().sessionAttribute("user", null))
		.andExpect(request().sessionAttribute("userNameData", null))
		.andExpect(request().attribute("loginMessage", "You have successfully logged out"))
		.andExpect(forwardedUrl("redirect:/convert"));
    }
}