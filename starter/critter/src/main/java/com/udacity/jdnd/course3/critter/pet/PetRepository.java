package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    // ***
    @Query("select p from Pet p where owner_id = :ownerId")
    List<Pet> findPetsByOwner(@Param("ownerId") Long ownerId);
    // ***
    @Query("select p.owner.id from Pet p where id = :id")
    Long findOwnerByPet(@Param("id") Long petId);

}
