package com.drivingschool.service.impl;


import com.drivingschool.model.Appointment;
import com.drivingschool.service.IAppointmentService;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentService extends GenericService<Appointment, String> implements IAppointmentService {

    public AppointmentService() {
        super(Appointment.class);
    }

    public Appointment findByMonitor(String username) {
        return entityManager.createNamedQuery("Appointment.findByMonitor",
                Appointment.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public Appointment findBySchoolId(int id) {
        return entityManager.createNamedQuery("Appointment.findBySchoolId",
                Appointment.class)
                .setParameter("schoolId", id)
                .getSingleResult();
    }

}
