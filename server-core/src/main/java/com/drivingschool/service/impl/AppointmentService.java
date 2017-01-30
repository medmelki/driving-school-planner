package com.drivingschool.service.impl;


import com.drivingschool.model.Appointment;
import com.drivingschool.service.IAppointmentService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentService extends GenericService<Appointment, String> implements IAppointmentService {

    public AppointmentService() {
        super(Appointment.class);
    }

    @Override
    public List<Appointment> findByInstructor(String username) {
        return entityManager.createNamedQuery("Appointment.findByInstructor",
                Appointment.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Appointment> findBySchoolId(int id) {
        return entityManager.createNamedQuery("Appointment.findBySchoolId",
                Appointment.class)
                .setParameter("schoolId", id)
                .getResultList();
    }

    @Override
    public List<Appointment> findByAdmin(String username) {
        return entityManager.createNamedQuery("Appointment.findByAdmin",
                Appointment.class)
                .setParameter("username", username)
                .getResultList();
    }

}
