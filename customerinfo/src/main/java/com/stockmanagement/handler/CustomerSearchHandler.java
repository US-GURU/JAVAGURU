package com.stockmanagement.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmanagement.jpa.CustomerRepository;
import com.stockmanagement.json.CustomerInfo;
import com.stockmanagement.json.Message;
import com.stockmanagement.po.CustomerDetails;

@RestController
public class CustomerSearchHandler {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/findByAge")
	public ResponseEntity<?> findCustomerByAge() {

		Collection<CustomerDetails> customersDetails = (Collection<CustomerDetails>) customerRepository.findAll();

		Collection<CustomerDetails> customersAbove18 = customersDetails.stream()
				.filter(c -> (c.getAge() >= 18))
				.collect(Collectors.toList());

		List<CustomerInfo> customersList = new ArrayList<CustomerInfo>();

		ResponseEntity<?> response = null;

		if (customersAbove18.isEmpty()) {

			Message message = new Message();
			message.setMessage("age above 18 not available");
			response = new ResponseEntity<Message>(message, HttpStatus.OK);
			return response;
		}

		customersAbove18.forEach(cd -> {
			CustomerInfo c = new CustomerInfo();
			c.setId(cd.getId());
			c.setName(cd.getName());
			c.setPhone(cd.getPhone());
			c.setAge(cd.getAge());
			customersList.add(c);
		});

		response = new ResponseEntity<List<CustomerInfo>>(customersList, HttpStatus.OK);
		return response;
		
	}

}
