'use strict';

angular.module('autoposApp')
    .controller('BillItemDetailController', function ($scope, $rootScope, $stateParams, entity, BillItem, Item, Bill) {
        $scope.billItem = entity;
        $scope.load = function (id) {
            BillItem.get({id: id}, function(result) {
                $scope.billItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:billItemUpdate', function(event, result) {
            $scope.billItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
