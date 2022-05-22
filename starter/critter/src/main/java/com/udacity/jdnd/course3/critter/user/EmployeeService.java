package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long employeeId){
        Optional<Employee> employeeToFind = employeeRepository.findById(employeeId);
        return employeeToFind.orElseThrow(NoSuchElementException::new);
    }

    public void setAvailability(Set<DayOfWeek> availability, Long employeeId){
        Employee employee = findEmployeeById(employeeId);
        employee.setDaysAvailable(availability);
        save(employee);


    }

    public List<Employee> findAvailableEmployees(LocalDate day, Set<EmployeeSkill> skills){
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        List<Employee> availableEmployees = new ArrayList<>();
        List<Employee> allEmployees = employeeRepository.findAll();

        for(Employee employee : allEmployees){
            if(employee.getDaysAvailable().contains(dayOfWeek) && employee.getSkills().containsAll(skills)){
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;

    }
}
