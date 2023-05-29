package com.example.bm.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.bm.dto.OrderRequestDTO;
import com.example.bm.dto.OrderResponseDTO;
import com.example.bm.enums.ItemCategory;
import com.example.bm.enums.UserCategory;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.Item;
import com.example.bm.modal.Order;
import com.example.bm.modal.User;
import com.example.bm.repository.ItemRepository;
import com.example.bm.repository.OrderRepository;
import com.example.bm.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderServiceImplTest {

	@LocalServerPort
	int randomServerPort;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Test
	void createOrderUserNullTest() throws URISyntaxException {
		OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder().userId(1l).build();

		when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.empty());

		assertThrows(CustomDataNotFoundException.class, () -> {
			orderServiceImpl.createOrder(orderRequestDTO);
		});
	}

	@Test
	void createOrderItemsNullTest() throws URISyntaxException {
		OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder().userId(1l).itemids(null).build();
		User user = User.builder().id(1l).email("testing@gmail.com").caterory(UserCategory.AFFILATED).build();
		when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.of(user));

		assertThrows(CustomDataNotFoundException.class, () -> {
			orderServiceImpl.createOrder(orderRequestDTO);
		});
	}

	@Test
	void createOrderUserTypeAFFILATEDTest() throws URISyntaxException {
		List<Long> itemList = new ArrayList<>();
		Item itemOne = Item.builder().id(1l).price(10d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemtwo = Item.builder().id(2l).price(20d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemthree = Item.builder().id(3l).price(50d).quantity(1d).caterory(ItemCategory.NONGROCERY).build();

		itemList.add(1l);
		itemList.add(2l);
		itemList.add(3l);
		OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder().userId(1l).itemids(itemList).build();
		User user = User.builder().id(1l).email("testing@gmail.com").caterory(UserCategory.AFFILATED).build();
		user.setCreatedDate(new Date());
		Order order = Order.builder().id(1l).build();

		when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.of(user));

		when(itemRepository.findById(1l)).thenReturn(Optional.of(itemOne));
		when(itemRepository.findById(2l)).thenReturn(Optional.of(itemtwo));
		when(itemRepository.findById(3l)).thenReturn(Optional.of(itemthree));

		when(orderRepository.save(ArgumentMatchers.any())).thenReturn(order);

		OrderResponseDTO response = orderServiceImpl.createOrder(orderRequestDTO);

		assertEquals(1l, response.getId());

	}

	@Test
	void createOrderUserTypeEmployeeTest() throws URISyntaxException {
		List<Long> itemList = new ArrayList<>();
		Item itemOne = Item.builder().id(1l).price(10d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemtwo = Item.builder().id(2l).price(20d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemthree = Item.builder().id(3l).price(50d).quantity(1d).caterory(ItemCategory.NONGROCERY).build();

		itemList.add(1l);
		itemList.add(2l);
		itemList.add(3l);
		OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder().userId(1l).itemids(itemList).build();
		User user = User.builder().id(1l).email("testing@gmail.com").caterory(UserCategory.EMPLOYEE).build();
		user.setCreatedDate(new Date());
		Order order = Order.builder().id(1l).build();

		when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.of(user));

		when(itemRepository.findById(1l)).thenReturn(Optional.of(itemOne));
		when(itemRepository.findById(2l)).thenReturn(Optional.of(itemtwo));
		when(itemRepository.findById(3l)).thenReturn(Optional.of(itemthree));

		when(orderRepository.save(ArgumentMatchers.any())).thenReturn(order);

		OrderResponseDTO response = orderServiceImpl.createOrder(orderRequestDTO);

		assertEquals(1l, response.getId());

	}

	@Test
	void createOrderUserTypeCUSTOMERTest() throws URISyntaxException {
		List<Long> itemList = new ArrayList<>();
		Item itemOne = Item.builder().id(1l).price(10d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemtwo = Item.builder().id(2l).price(20d).quantity(1d).caterory(ItemCategory.GROCERY).build();
		Item itemthree = Item.builder().id(3l).price(50d).quantity(1d).caterory(ItemCategory.NONGROCERY).build();

		itemList.add(1l);
		itemList.add(2l);
		itemList.add(3l);
		OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder().userId(1l).itemids(itemList).build();
		User user = User.builder().id(1l).email("testing@gmail.com").caterory(UserCategory.CUSTOMER).build();
		user.setCreatedDate(new Date("20/11/2010"));
		Order order = Order.builder().id(1l).build();

		when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.of(user));

		when(itemRepository.findById(1l)).thenReturn(Optional.of(itemOne));
		when(itemRepository.findById(2l)).thenReturn(Optional.of(itemtwo));
		when(itemRepository.findById(3l)).thenReturn(Optional.of(itemthree));

		when(orderRepository.save(ArgumentMatchers.any())).thenReturn(order);

		OrderResponseDTO response = orderServiceImpl.createOrder(orderRequestDTO);

		assertEquals(1l, response.getId());

	}

}