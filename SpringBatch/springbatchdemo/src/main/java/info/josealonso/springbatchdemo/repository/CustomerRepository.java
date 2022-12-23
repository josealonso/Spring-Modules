package info.josealonso.springbatchdemo.repository;

import info.josealonso.springbatchdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
