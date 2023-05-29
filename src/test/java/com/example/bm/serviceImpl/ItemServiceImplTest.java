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

import com.example.bm.dto.ItemRequestDTO;
import com.example.bm.dto.ItemResponseDTO;
import com.example.bm.dto.UpdateItemRequestDTO;
import com.example.bm.exception.CustomDataNotFoundException;
import com.example.bm.modal.Item;
import com.example.bm.repository.ItemRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemServiceImplTest {

	@LocalServerPort
	int randomServerPort;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	ItemServiceImpl itemServiceImpl;

	@Test
	void createItemCategoryNotFoundTest() throws URISyntaxException {
		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("category").build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.createItem(itemRequestDTO);
		});
	}

	@Test
	void createItemNameRequiredTest() throws URISyntaxException {
		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("GROCERRY").code(null).build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.createItem(itemRequestDTO);
		});
	}

	@Test
	void createItemQuantityRequiredTest() throws URISyntaxException {
		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("GROCERRY").name("username").build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.createItem(itemRequestDTO);
		});
	}

	@Test
	void createIteamPriceRequiredTest() throws URISyntaxException {
		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("GROCERRY").name("username").quantity("11")
				.build();

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.createItem(itemRequestDTO);
		});
	}

	@Test
	void createIteamCodeAlreadyExsistTest() throws URISyntaxException {
		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("GROCERRY").name("username").quantity("11")
				.code("code").build();

		Optional<Item> item = Optional.of(Item.builder().id(1l).build());
		when(itemRepository.findByItemCode(itemRequestDTO.getCode())).thenReturn(item);

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.createItem(itemRequestDTO);
		});
	}

	@Test
	void createItemTest() throws URISyntaxException {

		ItemRequestDTO itemRequestDTO = ItemRequestDTO.builder().category("GROCERY").name("username").price("10")
				.quantity("11").code("code").build();

		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("code").price(10d).quantity(2d).build());
		when(itemRepository.findByItemCode(itemRequestDTO.getCode())).thenReturn(Optional.empty());

		when(itemRepository.save(ArgumentMatchers.any())).thenReturn(item.get());

		ItemResponseDTO response = itemServiceImpl.createItem(itemRequestDTO);

		assertEquals("code", response.getCode());
	}

	@Test
	void updateItemNotFoundTest() throws URISyntaxException {
		UpdateItemRequestDTO itemRequestDTO = UpdateItemRequestDTO.builder().id(1l).category("category").build();
		when(itemRepository.findById(itemRequestDTO.getId())).thenReturn(Optional.empty());

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.updateItem(itemRequestDTO);
		});
	}

	@Test
	void updateItemCodeAlreadyExsistTest() throws URISyntaxException {
		UpdateItemRequestDTO itemRequestDTO = UpdateItemRequestDTO.builder().id(1l).category("category").build();

		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findByItemCode(itemRequestDTO.getCode())).thenReturn(item);
		when(itemRepository.findById(itemRequestDTO.getId())).thenReturn(item);

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.updateItem(itemRequestDTO);
		});
	}

	@Test
	void updateItemCategoryNotFoundTest() throws URISyntaxException {
		UpdateItemRequestDTO itemRequestDTO = UpdateItemRequestDTO.builder().id(1l).category("category").name("name")
				.quantity("10").price("112").build();

		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findByItemCode(itemRequestDTO.getCode())).thenReturn(item);
		when(itemRepository.findById(itemRequestDTO.getId())).thenReturn(item);

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.updateItem(itemRequestDTO);
		});
	}

	@Test
	void updateItemTest() throws URISyntaxException {
		UpdateItemRequestDTO itemRequestDTO = UpdateItemRequestDTO.builder().id(1l).category("GROCERY").name("name")
				.quantity("10").price("112").build();

		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findByItemCode(itemRequestDTO.getCode())).thenReturn(item);
		when(itemRepository.findById(itemRequestDTO.getId())).thenReturn(item);

		when(itemRepository.save(ArgumentMatchers.any())).thenReturn(item.get());

		ItemResponseDTO response = itemServiceImpl.updateItem(itemRequestDTO);
		assertEquals("codee", response.getCode());
	}

	@Test
	void readItemNotPresentTest() throws URISyntaxException {
		long id = 1l;
		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.readItem(id);
		});
	}

	@Test
	void readItemTest() throws URISyntaxException {
		long id = 1l;
		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").price(10d).quantity(11d).build());
		when(itemRepository.findById(id)).thenReturn(item);

		ItemResponseDTO response = itemServiceImpl.readItem(id);
		assertEquals("codee", response.getCode());
	}

	@Test
	void deleteItemNotPresentTest() throws URISyntaxException {
		long id = 1l;
		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findByIdAndActiveTrue(id)).thenReturn(null);

		assertThrows(CustomDataNotFoundException.class, () -> {
			itemServiceImpl.deleteItem(id);
		});
	}

	@Test
	void deleteItemTest() throws URISyntaxException {
		long id = 1l;
		Optional<Item> item = Optional.of(Item.builder().id(1l).itemCode("codee").build());
		when(itemRepository.findByIdAndActiveTrue(id)).thenReturn(item.get());

		itemServiceImpl.deleteItem(id);
	}

}
