'use strict';

angular.module('autoposApp')
    .controller('BillDetailController', function ($scope, $rootScope, $stateParams, entity, Bill, BillItem, Vehicle) {
        $scope.bill = entity;
        $scope.load = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:billUpdate', function(event, result) {
            $scope.bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
