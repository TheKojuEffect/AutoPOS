'use strict';

angular.module('autoposApp')
    .controller('ReceiptDetailController', function ($scope, $rootScope, $stateParams, entity, Receipt, Customer) {
        $scope.receipt = entity;
        $scope.load = function (id) {
            Receipt.get({id: id}, function(result) {
                $scope.receipt = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:receiptUpdate', function(event, result) {
            $scope.receipt = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
