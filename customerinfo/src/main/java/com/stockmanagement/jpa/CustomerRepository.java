package com.stockmanagement.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockmanagement.po.CustomerDetails;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerDetails, Integer>{
	 
}
