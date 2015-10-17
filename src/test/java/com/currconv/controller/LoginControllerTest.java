package com.currconv.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.currconv.delegate.UserDelegate;


@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private UserDelegate userDelegateMock;
    
    
    
    @Test
    public void displayLoginWithoutUserShouldReturnLogin() {
    	
    }
    
    @Test
    public void displayLoginWithUSerShouldGotoConvertCurrency() {
    	
    }
    
    
    @Test
    public void executeLoginWithBindingErrorsShouldStayInLoginPage() {
    	
    }
    
    @Test
    public void executeLoginInvalidUserShouldStayinLoginPage() {
    	
    }
    
    @Test
    public void executeLoginAuthenticalFailureShouldStayinLoginPage() {
    	
    }
    
    @Test
    public void executeLoginShouldPass(){
    	
    }

}