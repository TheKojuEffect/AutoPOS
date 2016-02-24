'use strict';

angular.module('autoposApp')
    .controller('PhoneNumberDetailController', function ($scope, $rootScope, $stateParams, entity, PhoneNumber) {
        $scope.phoneNumber = entity;
        $scope.load = function (id) {
            PhoneNumber.get({id: id}, function(result) {
                $scope.phoneNumber = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:phoneNumberUpdate', function(event, result) {
            $scope.phoneNumber = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
