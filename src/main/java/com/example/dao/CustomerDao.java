package com.example.dao;

import com.example.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 19/09/2016.
 */
public interface CustomerDao {
    List<Customer> getAllCustomer();
    Customer getCustommerById(long id);
    Customer getCustomerByLastName(String name);
    void createCustomer(Customer customer);
    ArrayList<String> spellingCheck(String content);
    String positionMistake(String content);
}
