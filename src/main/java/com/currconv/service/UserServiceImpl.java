package com.currconv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currconv.dao.UserDao;
import com.currconv.dto.LoginDTO;
import com.currconv.dto.UserActivityDTO;
import com.currconv.dto.UserDTO;
import com.currconv.entity.Address;
import com.currconv.entity.User;
import com.currconv.entity.UserActivity;
import com.currconv.exception.AuthenticationFailureException;
import com.currconv.exception.EmailAlreadyRegisteredException;
import com.currconv.exception.InvalidUserException;

@Service
public class UserServiceImpl implements UserService {
    	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	public void registerNewUser(final UserDTO userDTO) throws EmailAlreadyRegisteredException {
		if (emailExist(userDTO.getEmailID())) {
			throw new EmailAlreadyRegisteredException(
					"This email address already is already registered: " + userDTO.getEmailID());
		}
		userDao.save(this.populateUserData(userDTO));
		log.info("The Email ID has been registered ->"+ userDTO.getEmailID());
	}
	
	private boolean emailExist(final String email) {
		final User user = userDao.findByEmailID(email);
		return (user != null);
	}

	@Override
	public UserDTO authenticateUser(final LoginDTO loginDTO)
			throws InvalidUserException, AuthenticationFailureException {
		User user = userDao.findByEmailID(loginDTO.getEmailID());

		if (user == null) {
			throw new InvalidUserException("Invalid credentials");
		}

		if (!user.getPassword().equals(loginDTO.getPassword())) {
			throw new AuthenticationFailureException("Authentication failure");
		}
		log.info("The User has been authenticated ->"+ loginDTO.getEmailID());
		return loadFromUserObject(user);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private UserDTO loadFromUserObject(User user) {
		UserDTO userDTO = new UserDTO(user.getEmailID(), user.getPassword(), user.getFirstName(), user.getLastName(),
				user.getDateOfBirth(), user.getAddress().getAddressLine1(), user.getAddress().getAddressLine2(),
				user.getAddress().getStreet(), user.getAddress().getZip(), user.getAddress().getCity(),
				user.getAddress().getCountry());
		userDTO.setUserActivityList(retrieveUserActivityList(user));
		return userDTO;
	}

	@Override
	public List<UserActivityDTO> refreshUserActivityList(UserActivityDTO activityDTO) throws InvalidUserException {
		User user = userDao.findByEmailID(activityDTO.getEmailID());
		if (user == null) {
			throw new InvalidUserException("User is invalid");
		}

		// Populate the activity set
		Set<UserActivity> userActivitySet = user.getActivityHistorySet();
		UserActivity userActivity = populateUserActivityData(activityDTO);
		userActivity.setUser(user);
		userActivitySet.add(userActivity);

		userDao.save(user);
		log.info("The User Activity has been saved and refreshed");
		return retrieveUserActivityList(user);
	}

	private List<UserActivityDTO> retrieveUserActivityList(User user) {
		List<UserActivityDTO> activityList = new ArrayList<>();
		for (UserActivity userActivity : user.getActivityHistorySet()) {
			activityList.add(new UserActivityDTO(user.getEmailID(), userActivity.getFromCurrency(),
					userActivity.getToCurrency(), userActivity.getSourceAmount(), userActivity.getConvertedAmount(),
					userActivity.getConvertedDate(), userActivity.getCreatedDate()));
		}
		return activityList;
	}

	private UserActivity populateUserActivityData(UserActivityDTO activityDTO) {
		final UserActivity activity = new UserActivity();
		activity.setFromCurrency(activityDTO.getFromCurrency());
		activity.setToCurrency(activityDTO.getToCurrency());
		activity.setConvertedDate(activityDTO.getConvertedDate());
		activity.setSourceAmount(activityDTO.getSourceAmount());
		activity.setConvertedAmount(activityDTO.getConvertedAmount());
		activity.setCreatedDate(activityDTO.getCreatedDate());

		return activity;
	}

	private User populateUserData(UserDTO userDTO) {
		final User user = new User();
		user.setEmailID(userDTO.getEmailID());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		final Address address = new Address();
		address.setAddressLine1(userDTO.getAddressLine1());
		address.setAddressLine2(userDTO.getAddressLine2());
		address.setCity(userDTO.getCity());
		address.setCountry(userDTO.getCountry());
		address.setStreet(userDTO.getStreet());
		address.setZip(userDTO.getZip());
		address.setUser(user);

		user.setAddress(address);

		return user;
	}


}
