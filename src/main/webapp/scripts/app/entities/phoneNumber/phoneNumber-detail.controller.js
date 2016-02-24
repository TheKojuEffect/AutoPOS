'use strict';

angular.module('autopos')
    .controller('PhoneNumberDetailController', function ($scope, $rootScope, $stateParams, entity, PhoneNumber) {
        $scope.phoneNumber = entity;
        $scope.load = function (id) {
            PhoneNumber.get({id: id}, function(result) {
                $scope.phoneNumber = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:phoneNumberUpdate', function(event, result) {
            $scope.phoneNumber = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
