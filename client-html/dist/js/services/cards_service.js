'use strict';

app.factory('CardService', ['$window', '$http', '$q', 'CommonService', function ($window, $http, $q, CommonService) {

    var serviceURL = CommonService.appURL + '/card/';

    return {

        findAllCards: function () {
            return $http.get(serviceURL, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching cards');
                        return $q.reject(errResponse);
                    }
                );
        },

        findCardById: function (id) {
            return $http.get(serviceURL + id, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching card');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        createCard: function (card) {
            return $http.post(serviceURL, card, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating card');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        updateCard: function (card) {
            return $http.put(serviceURL, card, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating card');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteCard: function (cardId) {
            return $http.delete(serviceURL + cardId, {
                withCredentials: true
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting card');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);