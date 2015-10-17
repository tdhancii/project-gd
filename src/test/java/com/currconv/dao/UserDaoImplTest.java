package com.currconv.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.currconv.entity.User;
import com.currconv.test.util.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private Query mockedQuery;

    @InjectMocks
    UserDaoImpl daoImpl = new UserDaoImpl();

    @Test
    public void findByEmailIDNoUserFoundTest() {
	String emailID = "nouserfound@emailID.com";
	when(mockEntityManager.createQuery("SELECT u from User  u where lower(u.emailID) = :email"))
		.thenReturn(mockedQuery);
	when(mockedQuery.setParameter("email", emailID.toLowerCase())).thenReturn(mockedQuery);
	when(mockedQuery.getResultList()).thenReturn(null);

	User resultUser = daoImpl.findByEmailID(emailID);
	assertNull(resultUser);
    }

    @Test
    public void findByEmailIDUserFoundTest() {
	String emailID = "userfound@emailID.com";
	User user = TestObjectBuilder.fixtureUserModel();
	when(mockEntityManager.createQuery("SELECT u from User  u where lower(u.emailID) = :email"))
		.thenReturn(mockedQuery);
	when(mockedQuery.setParameter("email", emailID.toLowerCase())).thenReturn(mockedQuery);
	when(mockedQuery.getResultList()).thenReturn(Arrays.asList(user));

	User resultUser = daoImpl.findByEmailID(emailID);
	assertEquals(resultUser, user);
    }

}