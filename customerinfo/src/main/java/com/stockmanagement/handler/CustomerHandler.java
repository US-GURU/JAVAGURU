package com.stockmanagement.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockmanagement.jpa.CustomerRepository;
import com.stockmanagement.json.BusinessIdentity;
import com.stockmanagement.json.CustomerInfo;
import com.stockmanagement.json.CustomerInfoResponse;
import com.stockmanagement.json.Message;
import com.stockmanagement.po.CustomerDetails;

@RestController
public class CustomerHandler {//TO DO: GURU rename class to CustomerController

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody CustomerInfo info) {

		CustomerDetails po = new CustomerDetails();
		po.setName(info.getName());
		po.setPhone(info.getPhone());
		po.setAge(info.getAge());

		Message message = new Message();
		
		try {

			CustomerDetails details = customerRepository.save(po);

			CustomerInfoResponse custResponse = new CustomerInfoResponse();

			ResponseEntity<CustomerInfoResponse> response = null;

			if (po.equals(details)) {

				custResponse.setDirtyStatus("saved successfully! "+new Date());
				response = new ResponseEntity<CustomerInfoResponse>(custResponse, HttpStatus.OK);

				return response;
			}

			custResponse.setDirtyStatus("not saved. check log for details");
			response = new ResponseEntity<CustomerInfoResponse>(custResponse, HttpStatus.OK);
			return response;

		} catch (Exception e) {

			e.printStackTrace();
			message.setMessage(e.getCause().getCause().getMessage());
		}
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	@PutMapping("/update")
	public CustomerInfoResponse update(@RequestBody CustomerInfo info) {

		CustomerDetails details = new CustomerDetails();
		details.setId(info.getId());
		details.setName(info.getName());
		details.setPhone(info.getPhone());

		CustomerInfoResponse response = new CustomerInfoResponse();

		try {
			customerRepository.save(details);
			response.setDirtyStatus("updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			response.setDirtyStatus("not updated. check log for details");
		}

		return response;
	}

	@DeleteMapping("/delete")
	public CustomerInfoResponse delete(@RequestBody BusinessIdentity id) {

		CustomerInfoResponse response = new CustomerInfoResponse();

		try {
			customerRepository.deleteById(id.getId());
			response.setDirtyStatus("deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			response.setDirtyStatus("unable to delete. check log for details");
		}
		return response;
	}

	@GetMapping("/find")
	public ResponseEntity<?> getCustomerDetails(@RequestBody BusinessIdentity identity) {

		Optional<CustomerDetails> detailsOpt = null;
		ResponseEntity<?> response = null;
		try {
			detailsOpt = customerRepository.findById(identity.getId());
			if (detailsOpt != null && detailsOpt.get() != null) {
				CustomerDetails details = detailsOpt.get();
				CustomerInfo info = new CustomerInfo();
				info.setName(details.getName());
				info.setPhone(details.getPhone());
				response = new ResponseEntity<CustomerInfo>(info, HttpStatus.OK);
				return response;
			}
			return response;
		} catch (NoSuchElementException e) {
			Message info = new Message();
			info.setMessage(e.getMessage());
			response = new ResponseEntity<Message>(info, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			Message info = new Message();
			info.setMessage("customer details not read. check log for details");
			response = new ResponseEntity<Message>(info, HttpStatus.OK);
			return response;
		}
	}

	@GetMapping("/findAll")
	public List<CustomerInfo> findAll() {
		return findAllTraditional();
	}
	
	public List<CustomerInfo> findAllTraditional() {// for each - tranditional

		Iterable<CustomerDetails> customersDetails = customerRepository.findAll();
		List<CustomerInfo> customerInfos = new ArrayList<>();

		for (CustomerDetails details : customersDetails) {
			CustomerInfo info = new CustomerInfo();
			info.setId(details.getId());
			info.setName(details.getName());
			info.setPhone(details.getPhone());
			customerInfos.add(info);
		}
		
		return customerInfos;
	}
	
	public List<CustomerInfo>  findAllJava8forEach() {//for Each- java8
		
		Iterable<CustomerDetails> customersDetails = customerRepository.findAll();
		List<CustomerInfo> customerInfos = new ArrayList<>();

		customersDetails.forEach(details -> {
			CustomerInfo info = new CustomerInfo();
			info.setId(details.getId());
			info.setAge(details.getAge());
			info.setName(details.getName());
			info.setPhone(details.getPhone());
			customerInfos.add(info);
		});

		return customerInfos;
	}
	
	public List<CustomerInfo> findAllIfPresent() {// using lambda
		
		Iterable<CustomerDetails> customersDetails = customerRepository.findAll();
		List<CustomerInfo> customerInfos = new ArrayList<>();

		Optional<Iterable<CustomerDetails>> custDetailsOpt = Optional.ofNullable(customersDetails);
		custDetailsOpt.ifPresent(details -> {
			
			details.forEach(customer -> {
				CustomerInfo info = new CustomerInfo();
				info.setId(customer.getId());
				info.setAge(customer.getAge());
				info.setName(customer.getName());
				info.setPhone(customer.getPhone());
				customerInfos.add(info);
			});
			
		});
		
		return customerInfos;
	}
	
	public List<CustomerInfo> getCustomerDetailsWithActualAPIs() {
		
		Iterable<CustomerDetails> customersDetails = customerRepository.findAll();
		List<CustomerInfo> customerInfos = new ArrayList<>();
		
		Optional<Iterable<CustomerDetails>> custDetailsOpt = Optional.ofNullable(customersDetails);
		
		custDetailsOpt.ifPresent(new Consumer<Iterable<CustomerDetails>>() {
			
			@Override
			public void accept(Iterable<CustomerDetails> details) {
				
				details.forEach(
						
						new Consumer<CustomerDetails>() {

							@Override
							public void accept(CustomerDetails customer) {
								
								CustomerInfo info = new CustomerInfo();
								info.setId(customer.getId());
								info.setAge(customer.getAge());
								info.setName(customer.getName());
								info.setPhone(customer.getPhone());
								customerInfos.add(info);
							}
							
						}
					);
				
			}
		});
		
		return customerInfos;
	}

}

