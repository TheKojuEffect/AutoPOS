'use strict';

angular.module('autoposApp')
    .controller('PaymentDetailController', function ($scope, $rootScope, $stateParams, entity, Payment, Vendor) {
        $scope.payment = entity;
        $scope.load = function (id) {
            Payment.get({id: id}, function(result) {
                $scope.payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:paymentUpdate', function(event, result) {
            $scope.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
