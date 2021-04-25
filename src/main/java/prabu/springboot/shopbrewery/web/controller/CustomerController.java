package prabu.springboot.shopbrewery.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prabu.springboot.shopbrewery.web.model.CustomerDTO;
import prabu.springboot.shopbrewery.web.service.CustomerService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequestMapping(CustomerController.API_V_1_CUSTOMER)
@RestController
public class CustomerController {

    public static final String API_V_1_CUSTOMER = "/api/v1/customer";
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId")UUID customerId){
        return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        CustomerDTO customerDTO1 =  customerService.saveCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", API_V_1_CUSTOMER + "/" + customerDTO1.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId")UUID customerId,@Valid @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomerById(customerId);
    }


}
