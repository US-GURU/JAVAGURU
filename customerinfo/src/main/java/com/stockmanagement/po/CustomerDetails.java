package com.stockmanagement.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class CustomerDetails {

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column
	@NotEmpty
	@NotBlank
	@NotNull
	private String name;

	@Column
	@NotNull
	private Long phone;
	
	@Column
	@NotNull
	private Integer age;
}
