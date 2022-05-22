package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
    Pet petToSave = convertPetDTOtoPet(petDTO);
    return convertPetToPetDTO(petService.save(petToSave));


    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet petToFind = petService.findPetById(petId);
        return convertPetToPetDTO(petToFind);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.findAllPets();
        List<PetDTO> petDTOList = new ArrayList<PetDTO>();
        petList.forEach(pet -> petDTOList.add(convertPetToPetDTO(pet)));
        return petDTOList;

    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.findAllPetsByOwner(ownerId);
        List<PetDTO> petDTOList = new ArrayList<PetDTO>();
        petList.forEach(pet -> petDTOList.add(convertPetToPetDTO(pet)));
        return petDTOList;

    }


    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;

    }
    private Pet convertPetDTOtoPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setOwner(customerService.findCustomerById(petDTO.getOwnerId()));
        return pet;
    }

}

