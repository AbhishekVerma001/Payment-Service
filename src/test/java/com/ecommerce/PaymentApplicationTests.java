package com.ecommerce;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.dao.CustomerRepository;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.JwtResponse;
import com.ecommerce.dto.Purchase;
import com.ecommerce.dto.PurchaseResponse;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;


@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class PaymentApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	
	Purchase p=new Purchase();
	AuthRequest auth =new AuthRequest();
	
	@Autowired
	CustomerRepository customerRepository;

	@Test
	void contextLoads() {
	}
	@Test
    public void testPlaceOrder() throws Exception {
		final String tokenUrl = "http://localhost:8080/auth/token";
        URI tokenUri = new URI(tokenUrl);
        auth.setUsername("Abc@gmail.com");
		auth.setPassword("A1234567");
         
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.set("Content-Type", "application/json");
        
        
        
        HttpEntity<AuthRequest> tokenRequest = new HttpEntity<>(auth, tokenHeaders);
         
        ResponseEntity<JwtResponse> tokenResult = this.restTemplate.postForEntity(tokenUri, tokenRequest, JwtResponse.class);
        String token=tokenResult.getBody().getToken();
	
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer "+token);
        //HttpEntity<Product> request = new HttpEntity<>( headers);
		
		
		
		
		OrderItem oi=new OrderItem();
		oi.setImageUrl("assets/images/products/Men/Shoes/51.jpg");
		oi.setQuantity(1);
		oi.setUnitPrice(BigDecimal.valueOf(2970L));
		oi.setProductId(51L);
		//oi.setId(423L);
		
		Order o=new Order();
		o.setTotalPrice(BigDecimal.valueOf(2970L));
		o.setTotalQuantity(1);
		//o.setId(423L);
		
		
		
		
		Address  shippingAddress=new Address();
		shippingAddress.setCountry("India");
		shippingAddress.setState("Bihar");
		shippingAddress.setCity("Patna");
		shippingAddress.setStreet("Kamptee road");
		shippingAddress.setZipCode("443322");
		//shippingAddress.setId(424L);
		//shippingAddress.setOrder(o);
		
		Address  billingAddress=new Address();
		billingAddress.setCountry("India");
		billingAddress.setState("Maharashtra");
		billingAddress.setCity("Pune");
		billingAddress.setStreet("Kharadi road");
		billingAddress.setZipCode("445512");
		//billingAddress.setOrder(o);
		//billingAddress.setId(425L);
		
		Customer c=new Customer();
		c.setEmail("ajay@gmail.com");
		c.setFirstName("Ajay");
		c.setLastName("Rai");
		//c.setId(423L);
		//c.setId(420L);
		
		//o.setBillingAddress(billingAddress);
		//o.setCustomer(c);
		//o.setShippingAddress(shippingAddress);
		
		//CheckoutServiceImpl service=new CheckoutServiceImpl(customerRepository);
		//PurchaseResponse purchaseResponse = service.placeOrder(p);
		//Set<Order> orders = new HashSet<>();
		//orders.add(o);
		
		
		Set<OrderItem> orderItems = new HashSet<>();
		//oi.setOrder(o);
		
		orderItems.add(oi);
		//o.setOrderItems(orderItems);
		
		//c.setOrders(orders);
		p.setCustomer(c);
		p.setBillingAddress(billingAddress);
		p.setShippingAddress(shippingAddress);
		p.setOrder(o);
		p.setOrderItems(orderItems);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Authorization", "Bearer "+"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBYmNAZ21haWwuY29tIiwiaWF0IjoxNjgwNDQ4NjIwLCJleHAiOjE2ODA0NTA0MjB9.4YJBwPZ71CYr6xMQXqNXn5E2uIJ13WMW1QCMxVHr-8M");
        HttpEntity<Purchase> request = new HttpEntity<>(p, headers);
        
        //ResponseEntity<Product> result = this.restTemplate.postForEntity(uri, request, UserCredential.class);
        URI uri = new URI("/checkout/purchase");
        ResponseEntity<PurchaseResponse> entity = this.restTemplate.postForEntity(uri, request, PurchaseResponse.class);
               
        System.out.println(entity.getBody());
        Assertions.assertEquals(200, entity.getStatusCode().value());
        assertNotNull(entity);
    }
}
