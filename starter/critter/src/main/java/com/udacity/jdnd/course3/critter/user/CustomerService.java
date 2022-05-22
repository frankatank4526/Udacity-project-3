package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.DayOfWeek;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;


    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
    public Customer findCustomerById(Long id){
        Optional<Customer> customerToFind = customerRepository.findById(id);
        return customerToFind.orElseThrow(NoSuchElementException::new);
    }
    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findOwnerByPet(Long petId){
        return findCustomerById(petRepository.findOwnerByPet(petId));
    }


}
