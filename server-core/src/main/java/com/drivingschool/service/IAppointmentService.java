package com.drivingschool.service;

import com.drivingschool.model.Appointment;
import org.springframework.stereotype.Service;

@Service
public interface IAppointmentService extends IGenericService<Appointment, String> {
}
