package com.drivingschool.controller;

import com.drivingschool.model.Appointment;
import com.drivingschool.service.impl.AppointmentService;
import com.drivingschool.utils.PermissionUtils;
import com.drivingschool.utils.RolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> listAll() {

        List<Appointment> appointments = new ArrayList<>();

        switch (RolesUtils.getCurrentUserRole()) {
            case RolesUtils.ROLE_SUPERADMIN:
                break;
            case RolesUtils.ROLE_ADMIN:
                break;
            case RolesUtils.ROLE_INSTRUCTOR:
                break;
            case RolesUtils.ROLE_USER:
                break;
        }
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/appointment/", method = RequestMethod.POST)
    public ResponseEntity<Void> addAppointment(@RequestBody Appointment appointment) {
        appointmentService.create(appointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/appointment/", method = RequestMethod.PUT)
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
        appointmentService.update(appointment);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable String id) {

        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointmentService.delete(appointment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
