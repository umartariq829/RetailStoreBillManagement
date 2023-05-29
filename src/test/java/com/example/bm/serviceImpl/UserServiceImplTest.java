package com.example.bm.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.bm.dto.UpdateUserRequestDTO;
import com.example.bm.dto.UserRequestDTO;
import com.example.bm.dto.UserResponseDTO;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.User;
import com.example.bm.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceImplTest {

	@LocalServerPort
	int randomServerPort;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Test
	void createUserCategoryNotFoundTest() throws URISyntaxException {
		UserRequestDTO userRequestDTO = UserRequestDTO.builder().category("category").build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.createUser(userRequestDTO);
		});
	}
	
	
	@Test
	void createUserUserNameRequiredTest() throws URISyntaxException {
		UserRequestDTO userRequestDTO = UserRequestDTO.builder().category("Customer").userName(null).build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.createUser(userRequestDTO);
		});
	}
	
	@Test
	void createUserUserEmailRequiredTest() throws URISyntaxException {
		UserRequestDTO userRequestDTO = UserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.createUser(userRequestDTO);
		});
	}
	
	@Test
	void createUserTest() throws URISyntaxException {
		UserRequestDTO userRequestDTO = UserRequestDTO.builder().category("CUSTOMER").userName("username")
				.email("testing@gmail.com").firstName("first").lastName("last").build();

		User user = User.builder().id(1l).email("testing@gmail.com").build();
		when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

		UserResponseDTO response =
				userServiceImpl.createUser(userRequestDTO);
		
		assertEquals(1l, response.getId());
	}

	
	
	
	
	
	
	
	
	@Test
	void updateUserCategoryNotFoundTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("category").build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.updateUser(userRequestDTO);
		});
	}
	
	
	@Test
	void updateUserUserNameRequiredTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName(null).build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.updateUser(userRequestDTO);
		});
	}
	
	@Test
	void updateUserUserEmailRequiredTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.updateUser(userRequestDTO);
		});
	}
	
	@Test
	void updateUserTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email("testing@gmail.com").firstName("first").lastName("last").build();
	
		User user = User.builder().id(1l).email("testing@gmail.com").build();
		when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
				
				when(userRepository.findById(userRequestDTO.getId())).thenReturn(Optional.of(user));
		
		UserResponseDTO response =
				userServiceImpl.updateUser(userRequestDTO);
		
		assertEquals(1l, response.getId());
	}
	
	@Test
	void readUserNoUserFoundTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();
		Optional<User> user = Optional.empty();
		
		when(userRepository.findById(1l)).thenReturn(user);

		
		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.readUser(1l);
		});
	}
	
	
	
	@Test
	void readUserTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();
		Optional<User> user = Optional.of(User.builder().id(1l).firstName("firstname").build());
		
		when(userRepository.findById(1l)).thenReturn(user);

		
		UserResponseDTO userResponseDTO = userServiceImpl.readUser(1l);
		assertEquals(1l, userResponseDTO.getId());
	}
	
	
	
	@Test
	void deleteUserNoUserFoundTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();
				
		when(userRepository.findByIdAndActiveTrue(1l)).thenReturn(null);

		assertThrows(CustomDataNotFoundException.class, () -> {
			userServiceImpl.deleteUser(1l);
		});
	}
	
	
	
	@Test
	void deleteUserTest() throws URISyntaxException {
		UpdateUserRequestDTO userRequestDTO = UpdateUserRequestDTO.builder().category("Customer").userName("username")
				.email(null).build();
		User user = User.builder().id(1l).firstName("firstname").build();
		
		when(userRepository.findByIdAndActiveTrue(1l)).thenReturn(user);

		userServiceImpl.deleteUser(1l);
	}
	
	

}
