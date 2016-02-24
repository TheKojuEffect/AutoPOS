'use strict';

angular.module('autopos')
    .controller('VehicleDetailController', function ($scope, $rootScope, $stateParams, entity, Vehicle, Customer) {
        $scope.vehicle = entity;
        $scope.load = function (id) {
            Vehicle.get({id: id}, function(result) {
                $scope.vehicle = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:vehicleUpdate', function(event, result) {
            $scope.vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
