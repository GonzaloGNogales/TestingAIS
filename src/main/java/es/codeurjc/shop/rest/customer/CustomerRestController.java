package es.codeurjc.shop.rest.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.shop.domain.customer.Customer;
import es.codeurjc.shop.domain.customer.CustomerNotFoundException;
import es.codeurjc.shop.domain.customer.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    List<GetCustomerResponse> listCustomers() {
        return customerService.getCustomers()
            .stream()
            .map(c -> new GetCustomerResponse(c.getId(), c.getName(), c.getCredit()))
            .collect(Collectors.toList());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCredit());
        return new CreateCustomerResponse(customer.getId());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetCustomerResponse> getCustomer(@PathVariable long id) {
        try {
            Customer customer = customerService.getCustomer(id);
            GetCustomerResponse getCustomerResponse = new GetCustomerResponse(customer.getId(), customer.getName(), customer.getCredit());
            return new ResponseEntity<>(getCustomerResponse, HttpStatus.OK);
        } catch (CustomerNotFoundException customerNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCustomerResponse> updateItem(@RequestBody CreateCustomerRequest updatedItem, @PathVariable long id) {
        try {
            Customer customer = customerService.updateCustomer(id, updatedItem.getName(), updatedItem.getCredit());
            GetCustomerResponse getCustomerResponse = new GetCustomerResponse(customer.getId(), customer.getName(), customer.getCredit());
            return new ResponseEntity<>(getCustomerResponse, HttpStatus.CREATED);
        } catch (CustomerNotFoundException customerNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
