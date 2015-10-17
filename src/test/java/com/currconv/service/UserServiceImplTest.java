package com.currconv.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.currconv.dao.UserDao;
import com.currconv.dto.LoginDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.entity.User;
import com.currconv.entity.UserActivity;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.EmailAlreadyRegisteredException;
import com.currconv.exception.InvalidUserException;
import com.currconv.test.util.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl mockUserServiceImpl = new UserServiceImpl();

    @Mock
    private UserDao mockUserDao;

    @Test(expected = EmailAlreadyRegisteredException.class)
    public void testRegisterUserAlreadyRegisteredEmail() throws Throwable {
	User testUser = TestObjectBuilder.fixtureUserModel();
	UserDTO testUserDTO = TestObjectBuilder.fixtureUserDTO(); 
	when(mockUserDao.findByEmailID(testUserDTO.getEmailID())).thenReturn(testUser);
	mockUserServiceImpl.registerNewUser(testUserDTO);
    }

    @Test
    public void testRegisterNewUser() throws Throwable {
	UserDTO testUserDTO = TestObjectBuilder.fixtureUserDTO();
	when(mockUserDao.findByEmailID(testUserDTO.getEmailID())).thenReturn(null);
	mockUserServiceImpl.registerNewUser(testUserDTO);
    }

    @Test(expected = AuthenticationFailureException.class)
    public void testAuthenticationFailureForUser() throws Throwable {
	User retrievedUserFromDB = TestObjectBuilder.fixtureUserModel();
	LoginDTO loginDTO = TestObjectBuilder.fixtureLoginDTO();
	retrievedUserFromDB.setPassword("changedPassword");
	when(mockUserDao.findByEmailID(loginDTO.getEmailID())).thenReturn(retrievedUserFromDB);
	mockUserServiceImpl.authenticateUser(loginDTO);
    }

    @Test(expected = InvalidUserException.class)
    public void testInvalidUserTryingLogin() throws Throwable {
	LoginDTO loginDTO = TestObjectBuilder.fixtureLoginDTO();
	when(mockUserDao.findByEmailID(loginDTO.getEmailID())).thenReturn(null);
	mockUserServiceImpl.authenticateUser(loginDTO);
    }

    @Test
    public void testAuthenticateValidUser() throws Throwable {
	User testUser = TestObjectBuilder.fixtureUserModel();
	testUser.setActivityHistorySet(new HashSet<UserActivity>());
	testUser.getActivityHistorySet().add(TestObjectBuilder.fixtureUserActivityModel());
	
	LoginDTO loginDTO = TestObjectBuilder.fixtureLoginDTO();
	when(mockUserDao.findByEmailID(loginDTO.getEmailID())).thenReturn(testUser);
	mockUserServiceImpl.authenticateUser(loginDTO);
    }

    @Test(expected = InvalidUserException.class)
    public void testRefreshUserActivityForInvalidUser() throws Throwable {
	UserActivityDTO testUserActivityDTO = TestObjectBuilder.fixtureUserActivityDTO();
	when(mockUserDao.findByEmailID(testUserActivityDTO.getEmailID())).thenReturn(null);
	mockUserServiceImpl.refreshUserActivityList(testUserActivityDTO);
    }

    @Test
    public void testRefreshUserActivityForValidUser() throws Throwable {
	User testUser = TestObjectBuilder.fixtureUserModel();
	testUser.setActivityHistorySet(new HashSet<UserActivity>());
	testUser.getActivityHistorySet().add(TestObjectBuilder.fixtureUserActivityModel());

	UserActivityDTO testUserActivityDTO = TestObjectBuilder.fixtureUserActivityDTO();
	when(mockUserDao.findByEmailID(testUserActivityDTO.getEmailID())).thenReturn(testUser);

	mockUserServiceImpl.refreshUserActivityList(testUserActivityDTO);
    }
}
