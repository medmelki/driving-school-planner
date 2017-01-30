package com.drivingschool.service;

import com.drivingschool.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAppointmentService extends IGenericService<Appointment, String> {

    List<Appointment> findByInstructor(String username);

    List<Appointment> findBySchoolId(int id);

    List<Appointment> findByAdmin(String username);
}
