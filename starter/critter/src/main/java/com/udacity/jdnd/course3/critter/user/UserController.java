package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       return convertCustomerToCustomerDTO(customerService.save(convertCustomerDTOToCustomer(customerDTO)));

    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customerService.findAllCustomers().forEach(customer -> customerDTOList.add(convertCustomerToCustomerDTO(customer)));
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(customerService.findOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        return convertEmployeeToEmployeeDTO(employeeService.save(employee));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesForService = employeeService.findAvailableEmployees(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeesForService.forEach(employee -> employeeDTOList.add(convertEmployeeToEmployeeDTO(employee)));
        return employeeDTOList;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null) {
            List<Long> petIdList = new ArrayList<>();
            customer.getPets().forEach(pet -> petIdList.add(pet.getId()));
            customerDTO.setPetIds(petIdList);
        }
        return customerDTO;
    }
    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);
        if(customerDTO.getPetIds() != null) {
            List<Pet> petList = new ArrayList<>();
            customerDTO.getPetIds().forEach(petId -> petList.add(petService.findPetById(petId)));

            customer.setPets(petList);
        }
        return customer;
    }
    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
