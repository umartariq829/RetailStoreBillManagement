package com.example.bm.modal;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.bm.enums.ItemCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "item")
public class Item extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String itemCode;

	@Column(name = "active")
	private boolean active;

	@Column(name = "price")
	private Double price;

	@Column(name = "quantity")
	private Double quantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private ItemCategory caterory;

}
