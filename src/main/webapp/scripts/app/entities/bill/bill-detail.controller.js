'use strict';

angular.module('autopos')
    .controller('BillDetailController', function ($scope, $rootScope, $stateParams, entity, Bill, BillItem, Vehicle) {
        $scope.bill = entity;
        $scope.load = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:billUpdate', function(event, result) {
            $scope.bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
