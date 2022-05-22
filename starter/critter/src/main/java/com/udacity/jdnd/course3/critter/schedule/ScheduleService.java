package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Schedule saveSchedule(Schedule schedule){
        Schedule scheduleToReturn = scheduleRepository.save(schedule);
        if(scheduleToReturn.getPets() != null){
            List<Pet> petsToUpdate = new ArrayList<>(scheduleToReturn.getPets());
            petsToUpdate.forEach(pet -> {
                pet.addSchedule(scheduleToReturn);
                petRepository.save(pet);
            });
        }
        if(scheduleToReturn.getEmployees() != null){
            List<Employee> employeesToUpdate = new ArrayList<>(scheduleToReturn.getEmployees());
            employeesToUpdate.forEach(employee->{
                employee.addSchedule(scheduleToReturn);
                employeeRepository.save(employee);
            });
        }
        return scheduleToReturn;
    }
    public List<Schedule> findAllSchedules(){
        return scheduleRepository.findAll();

    }
    public List<Schedule> getALlSchedulesForPet(Long petId){
        return scheduleRepository.findAllSchedulesForPet(petId);

    }

    public List<Schedule> getAllSchedulesForEmployee(Long employeeId){

//        List<Long> scheduleIdList = scheduleRepository.findAllSchedulesForEmployee(employeeId);
//        List<Schedule> scheduleList = new ArrayList<>();
//
//
//        scheduleIdList.forEach(scheduleId -> scheduleList.add(scheduleRepository.findById(scheduleId)));
//        return scheduleList;
         return scheduleRepository.findAllSchedulesForEmployee(employeeId);
    }
    public List<Schedule> findAllSchedulesForCustomer(Long customerId){
        List<Pet> petList = new ArrayList<>(petService.findAllPetsByOwner(customerId));
        List<Schedule> scheduleList = new ArrayList<>();
        petList.forEach(pet -> {
            scheduleList.addAll(pet.getSchedules());
        });
        return scheduleList;
    }



}
