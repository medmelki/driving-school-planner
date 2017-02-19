'use strict';

app.controller('CardController', ['$rootScope', '$scope', 'Upload', 'CardService', 'CommonService',
    function ($rootScope, $scope, Upload, CardService, CommonService) {

        var self = this;
        self.card = {};
        self.cards = [];
        $scope.updateMode = 0;

        self.findAllCards = function () {
            CardService.findAllCards()
                .then(
                    function (d) {
                        self.cards = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching Cards');
                    }
                );
        };

        self.findCardById = function (id) {
            CardService.findCardById(id)
                .then(
                    function (d) {
                        self.cards = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching card');
                    }
                );
        };

        self.createCard = function (card) {
            CardService.createCard(card)
                .then(
                    self.findAllCards,
                    function (errResponse) {
                        console.error('Error while creating Card.');
                    }
                );
        };

        self.updateCard = function (card) {
            CardService.updateCard(card)
                .then(
                    self.findAllCards,
                    function (errResponse) {
                        console.error('Error while updating Card.');
                    }
                );
        };

        self.deleteCard = function (id) {
            CardService.deleteCard(id)
                .then(
                    self.findAllCards,
                    function (errResponse) {
                        console.error('Error while deleting Card.');
                    }
                );
        };

        self.findAllCards();

        self.submit = function (card, isUpdateMode) {
            card.time = new Date(card.time).getTime();
            $scope.updateMode = isUpdateMode;
            console.log($scope.updateMode);
            if ($scope.updateMode === 0) {
                console.log('Saving New Card', card);
                self.createCard(card);
            } else {
                self.updateCard(card);
                console.log('Card updated with id ', card.id);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.cards.length; i++) {
                if (self.cards[i].id === id) {
                    self.card = angular.copy(self.cards[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.card.id === id) {//clean form if the card to be deleted is shown there.
                self.reset();
            }
            self.deleteCard(id);
        };

        self.reset = function () {
            self.card = {};
        };

        self.populateModal = function (card) {
            $scope.updateMode = 1;
            self.reset();
            self.card = card;
        };

        self.setAddMode = function () {
            $scope.updateMode = 0;
        };

    }])
;
