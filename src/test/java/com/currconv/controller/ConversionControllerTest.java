package com.currconv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.currconv.delegate.CurrencyConversionDelegate;
import com.currconv.delegate.MasterDataDelegate;
import com.currconv.delegate.UserDelegate;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.spring.BaseConfiguration;
import com.currconv.spring.WebConfiguration;
import com.currconv.test.util.ApplicationObjectBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfiguration.class,
	BaseConfiguration.class }, loader = AnnotationConfigWebContextLoader.class)
public class ConversionControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Mock
    private CurrencyConversionDelegate mockConversionDelegate;

    @Mock
    private UserDelegate mockUserDelegate;

    @Mock
    private MasterDataDelegate mockMasterDataDelegate;

    private MockMvc mockMvc;

    @Before
    public void setup() {
	// MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void loadConvertCurrencyValidNewUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());

	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", new ArrayList<UserActivityDTO>())).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }

    @Test
    public void loadConvertCurrencyValidExistingUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }

    @Test
    public void loadConvertCurrencyInvalidUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", null);
	
	mockMvc.perform(get("/convert").session(session))
		.andExpect(request().sessionAttribute("user", null))	
		.andExpect(model().attribute("userActivityList", new ArrayList<>()))
		.andExpect(view().name("redirect:/login"))
		.andExpect(request().sessionAttribute("loginMessage", "No user associated with the current session! Please re-login"))
		.andExpect(status().isOk());
    }
    
    @Test
    public void convertCurrencyValidNewUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }
    
    @Test
    public void convertCurrencyValidExistingUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }
    
    @Test
    public void convertCurrencyInvalidUserTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }
    
    @Test
    public void convertCurrencyCurrencyConversionNotSupportedTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }
    
    @Test
    public void convertCurrencyRemoteExceptionTest() throws Exception {

	MockHttpSession session = new MockHttpSession();
	UserDTO userDTO = ApplicationObjectBuilder.buildUserDTODummy();
	List<UserActivityDTO> userActivityList = new ArrayList<>();
	userDTO.setUserActivityList(userActivityList);
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());
	userActivityList.add(ApplicationObjectBuilder.buildUserActivityDTODummy());

	session.setAttribute("user", ApplicationObjectBuilder.buildUserDTODummy());
	mockMvc.perform((get("/convert")).session(session)).andExpect(status().isOk())
		.andExpect(model().attribute("userActivityList", userActivityList)).andExpect(
			request().sessionAttribute("userName", userDTO.getFirstName() + " " + userDTO.getLastName()));
    }
    
    
    

}
