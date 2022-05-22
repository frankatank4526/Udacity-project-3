package com.udacity.jdnd.course3.critter.schedule;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.findAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getALlSchedulesForPet(petId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getAllSchedulesForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.findAllSchedulesForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDTOList.add(convertScheduleToScheduleDTO(schedule)));
        return scheduleDTOList;

    }
    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> petIdList = new ArrayList<>();
        List<Long> employeeIdList = new ArrayList<>();
        if(schedule.getPets() != null){

            schedule.getPets().forEach(pet -> petIdList.add(pet.getId()));
        }
        if(schedule.getEmployees() != null){

            schedule.getEmployees().forEach(employee -> employeeIdList.add(employee.getId()));

        }
        scheduleDTO.setPetIds(petIdList);
        scheduleDTO.setEmployeeIds(employeeIdList);
        return scheduleDTO;
    }
    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Pet> petList = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();

        if(scheduleDTO.getPetIds() != null){
            scheduleDTO.getPetIds().forEach(petId -> petList.add(petService.findPetById(petId)));
        }
        if(scheduleDTO.getEmployeeIds() != null){
            scheduleDTO.getEmployeeIds().forEach(employeeId -> employeeList.add(employeeService.findEmployeeById(employeeId)));
        }
        schedule.setPets(petList);
        schedule.setEmployees(employeeList);
        return schedule;
    }
}
