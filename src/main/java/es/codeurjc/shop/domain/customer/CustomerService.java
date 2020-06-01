package es.codeurjc.shop.domain.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	public Customer createCustomer(String name, long credit) {
		Customer customer = new Customer(name, credit);
		return customerRepository.save(customer);
	}

	public Customer getCustomer(long id) throws CustomerNotFoundException {
		return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
	}

	public Customer updateCustomer(long id, String name, long credit) throws CustomerNotFoundException {
		Customer customer = getCustomer(id);
		customer.setName(name);
		customer.setCredit(credit);
		return customer;
	}

	public void reserveCredit(long customerId, long purchaseTotal)
			throws CustomerCreditLimitExceededException, CustomerNotFoundException {
		Customer customer = getCustomer(customerId);
		customer.reserveCredit(purchaseTotal);
	}
}
