package com.drivingschool.service.impl;


import com.drivingschool.model.Appointment;
import com.drivingschool.service.IAppointmentService;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentService extends GenericService<Appointment, String> implements IAppointmentService {

    public AppointmentService() {
        super(Appointment.class);
    }
}
