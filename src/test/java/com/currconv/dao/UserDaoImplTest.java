package com.currconv.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.currconv.entity.User;

@Repository
@Transactional
public class UserDaoImplTest {

	Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

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

	public void save(User user) {
		entityManager.persist(user);
		log.debug("The User has been saved"+ user.getEmailID());
	}

}