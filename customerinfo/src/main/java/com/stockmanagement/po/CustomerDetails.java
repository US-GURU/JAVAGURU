package com.stockmanagement.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
