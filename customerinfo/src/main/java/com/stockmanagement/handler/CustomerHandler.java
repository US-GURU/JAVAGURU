package com.stockmanagement.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CustomerHandler {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerHandler.class);

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
			
			if (po.equals(details)) {

				custResponse.setDirtyStatus("saved successfully! "+new Date());
				return new ResponseEntity<CustomerInfoResponse>(custResponse, HttpStatus.OK);
			}

			custResponse.setDirtyStatus("not saved. check log for details");
			return new ResponseEntity<CustomerInfoResponse>(custResponse, HttpStatus.OK);
		} catch (Exception e) {

			log.info("Customer details not saved");
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
			log.info("Customer details not updated");
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
			log.info("Customer details not deleted");
			response.setDirtyStatus("unable to delete. check log for details");
		}
		return response;
	}

	@GetMapping("/find")
	public ResponseEntity<?> findCustomerDetails(@RequestBody BusinessIdentity identity) {

		Optional<CustomerDetails> detailsOpt = null;	
		CustomerInfo customerInfo = null;
		Message msgInfo = new Message();
		try {
			detailsOpt = customerRepository.findById(identity.getId());
			if (detailsOpt != null && detailsOpt.get() != null) {
				CustomerDetails details = detailsOpt.get();
				customerInfo = new CustomerInfo();
				customerInfo.setName(details.getName());
				customerInfo.setPhone(details.getPhone());					
			}
			return new ResponseEntity<CustomerInfo>(customerInfo, HttpStatus.OK);	
		} catch (NoSuchElementException e) {
			
			msgInfo.setMessage(e.getMessage());
			return new ResponseEntity<Message>(msgInfo, HttpStatus.OK);	
		} catch (Exception e) {
			log.info("Customer details not find");
			Message info = new Message();
			info.setMessage("customer details not find. check log for details");
			return new ResponseEntity<Message>(info, HttpStatus.OK);			
		}
	}

	@GetMapping("/findAll")
	public List<CustomerInfo> findAll() {
		return findAllIfPresent();
	}
	
		
	private List<CustomerInfo> findAllIfPresent() {// using lambda
		
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
}