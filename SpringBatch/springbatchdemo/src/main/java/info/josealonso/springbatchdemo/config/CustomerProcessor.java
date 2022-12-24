package info.josealonso.springbatchdemo.config;

import info.josealonso.springbatchdemo.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override 
    public Customer process(Customer customer) throws Exception {
        if (customer.getGender().equalsIgnoreCase("male")) {
            return customer;
        } else {
            return null;
        }
    }
}
