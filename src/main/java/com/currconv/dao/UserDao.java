package com.currconv.dao;

import com.currconv.entity.User;

/**
 * Database
 */
public interface UserDao 
{
	void save(User user);
	
	User findByEmailID(String emailID);
}
