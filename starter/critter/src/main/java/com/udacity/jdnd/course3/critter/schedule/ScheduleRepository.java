package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class ScheduleRepository  {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PetService petService;
    private static final String findAllSchedules = "select * from schedule";
    private static final String findAllSchedulesForPet = "select schedule_id from schedule_pets where pets_id = :petId";
    private static final String findAllSchedulesForEmployee = "select schedule_id from schedule_employees where employees_id = :employeeId";

    public Schedule save(Schedule schedule){
        if (entityManager.find(Schedule.class, schedule.getId()) == null) {
            schedule.setId(null);
            entityManager.persist(schedule);
        }
        else{
            entityManager.merge(schedule);
        }
        return schedule;
    }
    public List<Schedule> findAll(){
       return entityManager.createNativeQuery(findAllSchedules).getResultList();

    }
    public Schedule findById(Long scheduleId){
        return entityManager.find(Schedule.class, scheduleId);
    }

    public List<Schedule> findAllSchedulesForPet(Long petId){
        return entityManager.createNativeQuery(findAllSchedulesForPet).setParameter("petId", petId).getResultList();
    }

    public List<Schedule> findAllSchedulesForEmployee(Long employeeId){
        return entityManager.createNativeQuery(findAllSchedulesForEmployee).setParameter("employeeId", employeeId).getResultList();
    }

}
