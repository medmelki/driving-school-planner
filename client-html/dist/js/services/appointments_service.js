'use strict';

app.factory('AppointmentService', ['$window', '$http', '$q', 'CommonService', function ($window, $http, $q, CommonService) {

    var serviceURL = CommonService.appURL + '/appointment/';

    return {

        findAllAppointments: function () {
            return $http.get(serviceURL, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching appointments');
                        return $q.reject(errResponse);
                    }
                );
        },

        findAppointmentById: function (id) {
            return $http.get(serviceURL + id, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching appointment');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        createAppointment: function (appointment) {
            return $http.post(serviceURL, appointment, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating appointment');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        updateAppointment: function (appointment) {
            return $http.put(serviceURL, appointment, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating appointment');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteAppointment: function (appointmentId) {
            return $http.delete(serviceURL + appointmentId, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting appointment');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);