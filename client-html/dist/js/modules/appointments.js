'use strict';

app.controller('AppointmentController', ['$rootScope', '$scope', 'Upload', 'AppointmentService', 'CommonService',
    function ($rootScope, $scope, Upload, AppointmentService, CommonService) {

        var self = this;
        self.appointment = {};
        self.appointments = [];
        self.updateMode = 0;

        self.findAllAppointments = function () {
            AppointmentService.findAllAppointments()
                .then(
                    function (d) {
                        self.appointments = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching Appointments');
                    }
                );
        };

        self.findAppointmentById = function (id) {
            AppointmentService.findAppointmentById(id)
                .then(
                    function (d) {
                        self.appointments = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching appointment');
                    }
                );
        };

        self.createAppointment = function (appointment) {
            AppointmentService.createAppointment(appointment)
                .then(
                    self.findAllAppointments,
                    function (errResponse) {
                        console.error('Error while creating Appointment.');
                    }
                );
        };

        self.updateAppointment = function (appointment) {
            AppointmentService.updateAppointment(appointment)
                .then(
                    self.findAllAppointments,
                    function (errResponse) {
                        console.error('Error while updating Appointment.');
                    }
                );
        };

        self.deleteAppointment = function (id) {
            AppointmentService.deleteAppointment(id)
                .then(
                    self.findAllAppointments,
                    function (errResponse) {
                        console.error('Error while deleting Appointment.');
                    }
                );
        };

        self.findAllAppointments();

        self.submit = function (appointment) {
            appointment.monitor.authorities = null;
            if (self.updateMode === 0) {
                console.log('Saving New Appointment', appointment);
                self.createAppointment(appointment);
            } else {
                self.updateAppointment(appointment);
                console.log('Appointment updated with id ', appointment.id);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.appointments.length; i++) {
                if (self.appointments[i].id === id) {
                    self.appointment = angular.copy(self.appointments[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.appointment.id === id) {//clean form if the appointment to be deleted is shown there.
                self.reset();
            }
            self.deleteAppointment(id);
        };

        self.reset = function () {
            self.appointment = {};
        };

        self.populateModal = function (appointment) {
            $scope.updateMode = 1;
            self.reset();
            self.appointment = appointment;
        };

        self.setAddMode = function () {
            $scope.updateMode = 0;
        };

    }])
;
