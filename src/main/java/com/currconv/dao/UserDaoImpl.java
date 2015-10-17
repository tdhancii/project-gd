package com.currconv.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.currconv.entity.User;

/**
 * Implementation class for Application User data access object
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    	/*Logger for printing log messages*/
	Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	/*Entity Manager reference*/
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method is used to search for an existing user using the emailID.
	 */
	@SuppressWarnings("unchecked")
	public User findByEmailID(String emailID) {
	    
		List<User> userList = entityManager.createQuery("SELECT u from User  u where lower(u.emailID) = :email")
				.setParameter("email", emailID.toLowerCase()).getResultList();
		
		if (userList != null && !userList.isEmpty()) {
			log.debug("User found for corresponding Email ID -> "+ emailID);
			return userList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * This method is used to persist the Application User to the database.
	 */
	public void save(User user) {
		entityManager.persist(user);
		log.debug("The User has been saved"+ user.getEmailID());
	}

}