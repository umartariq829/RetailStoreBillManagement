package com.example.bm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bm.Utils.Utils;
import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.enums.UserCategory;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.User;
import com.example.bm.repository.UserRepository;
import com.example.bm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
			+ "A-Z]{2,7}$";

	Pattern pat = Pattern.compile(emailRegex);

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

	@Override
	public UserResponseDTO createUser(UserRequestDTO request) {

		try {

			UserCategory userCategory = Utils.getUserCategory(request.getCategory());
			if (userCategory == null) {
				logger.error("Inside UserServiceImpl-->createUser--category does not exists");
				throw new CustomDataNotFoundException("user category does not exists");
			}
			if (Utils.isNullOrEmpty(request.getUserName())) {
				logger.error("Inside UserServiceImpl-->createUser--user name is required");
				throw new CustomDataNotFoundException("user name is required");
			}
			if (userRepository.findByUserName(request.getUserName()).isPresent()) {
				logger.error("Inside UserServiceImpl-->createUser--username {}");
				throw new CustomDataNotFoundException("username already exsists");
			}
	
			if (Utils.isNullOrEmpty(request.getEmail()) || !pat.matcher(request.getEmail()).matches()) {
				logger.error("Inside UserServiceImpl-->createUser--invalid email");
				throw new CustomDataNotFoundException("invalid email");
			}
		
			// Create new user's account
			User user = User.builder()
					.email(request.getEmail()).caterory(userCategory).userName(request.getUserName())
					.firstName(request.getFirstName()).lastName(request.getLastName())
					.active(true).build();
			user.setCreatedDate(new Date());
			
			User userSaved = userRepository.save(user);	
			
			return UserResponseDTO.builder().id(userSaved.getId()).email(userSaved.getEmail())
					.firstName(userSaved.getFirstName()).lastName(userSaved.getLastName())
					.category(userSaved.getCaterory()).userName(userSaved.getUserName()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->createUser-->= inside catch: {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	@Override
	public UserResponseDTO updateUser(UpdateUserRequestDTO updateData) {
		logger.info("Inside UserServiceImpl-->updateUser");
		Optional<User> user = userRepository.findById(updateData.getId());
		try {
			if (user.isEmpty()) {
				logger.error("Inside UserServiceImpl-->updateUser--NOUSERFOUND {}", user);
				throw new CustomDataNotFoundException("no user found");
			}

			User userData = user.get();
			if (updateData.getUserName() != null) {
				if (userRepository.findByUserName(updateData.getUserName()).isPresent()
						&& !updateData.getUserName().equals(userData.getUserName())) {
					logger.error("Inside UserServiceImpl-->updateUser--username {}");
					throw new CustomDataNotFoundException("user name present");
				}
				userData.setUserName(updateData.getUserName());
			}

			if (updateData.getEmail() == null || !pat.matcher(updateData.getEmail()).matches()) {
				logger.error("Inside UserServiceImpl-->updateUserRequestDTO-->updateUser");
				throw new CustomDataNotFoundException("invalid email");
			}
			userData.setEmail(updateData.getEmail());

			if (!Utils.isNullOrEmpty(updateData.getFirstName()))
				userData.setFirstName(updateData.getFirstName());

			if (!Utils.isNullOrEmpty(updateData.getLastName()))
				userData.setLastName(updateData.getLastName());

			User userSaved = userRepository.save(userData);
			return UserResponseDTO.builder().id(userSaved.getId()).email(userSaved.getEmail())
					.firstName(userSaved.getFirstName()).lastName(userSaved.getLastName())
					.category(userSaved.getCaterory()).userName(userSaved.getUserName()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->updateUserRequestDTO-->updateUser-inside catch: {}",
					e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	
	@Override
	public UserResponseDTO readUser(long id) {
		logger.info("Inside UserServiceImpl-->readUser-id {}", id);
		try {
			Optional<User> user = userRepository.findById(id);
			if (!user.isPresent()) {
				logger.error("Inside UserServiceImpl-->readUser-id {}", id);
				throw new CustomDataNotFoundException("no user found");
			}

			User data = user.get();
			return UserResponseDTO.builder().id(data.getId()).email(data.getEmail()).firstName(data.getFirstName())
					.lastName(data.getLastName()).category(data.getCaterory()).userName(data.getUserName()).build();

		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->readUser-inside catch: {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

	@Override
	public void deleteUser(long id) {
		logger.info("Inside UserServiceImpl-->deleteUser-id {}", id);
		try {
			User user = userRepository.findByIdAndActiveTrue(id);

			if (user == null) {
				logger.error("Inside UserServiceImpl-->deleteUser-id {}", id);
				throw new CustomDataNotFoundException("no user found");
			}
			user.setActive(false);
			userRepository.save(user);
		} catch (Exception e) {
			logger.error("Inside UserServiceImpl-->deleteUser-inside catch {}", e.getLocalizedMessage());
			throw new CustomDataNotFoundException((e.getLocalizedMessage()));
		}
	}

}
