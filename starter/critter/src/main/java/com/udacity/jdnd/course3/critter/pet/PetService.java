package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Here I am saving a pet
     *
     * @author Frank G
     * @version 1.0
     * @since 1.0
     * @see https://knowledge.udacity.com/questions/326282
     */
    public Pet save(Pet pet){
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getOwner();
        customer.addPet(returnedPet);
        customerRepository.save(customer);

        return returnedPet;
    }

    public Pet findPetById(long petId){
        Optional<Pet> petToFind = petRepository.findById(petId);
        return petToFind.orElseThrow(NoSuchElementException::new);

    }

    public List<Pet> findAllPets(){
        return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> findAllPetsByOwner(long ownerId){
        return (List<Pet>) petRepository.findPetsByOwner(ownerId);
    }

}
