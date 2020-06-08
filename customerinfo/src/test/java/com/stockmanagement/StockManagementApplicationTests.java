package com.stockmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmanagement.jpa.CustomerRepository;
import com.stockmanagement.po.CustomerDetails;

@SpringBootTest
class StockManagementApplicationTests {

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest 
{
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	CustomerDetails customerDetails;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


	@Test
	public void testFindTheCustomerData() {
		CustomerDetails po = new CustomerDetails();
		po.setName("cust122");
		po.setPhone(123L);
		po.setAge(35);
		CustomerDetails details = customerRepository.save(po);
		assertEquals("cust122", details.getName());
	}
	
  

}
}