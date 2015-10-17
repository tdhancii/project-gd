package com.currconv.dao;

import com.currconv.entity.User;

/**
 * Interface for Application User data access object
 * @author gauravD
 */
public interface UserDao 
{
	void save(User user);
	
	User findByEmailID(String emailID);
}
