package prabu.springboot.shopbrewery.web.service;

import org.springframework.stereotype.Service;
import prabu.springboot.shopbrewery.web.model.CustomerDTO;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO getCustomerById(UUID customerId) {
        return CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("jhon")
                .build();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return CustomerDTO.builder().id(UUID.randomUUID()).name("name").build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }
}
