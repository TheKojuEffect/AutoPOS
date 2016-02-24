'use strict';

angular.module('autoposApp')
    .controller('LedgerDetailController', function ($scope, $rootScope, $stateParams, entity, Ledger, Customer) {
        $scope.ledger = entity;
        $scope.load = function (id) {
            Ledger.get({id: id}, function(result) {
                $scope.ledger = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:ledgerUpdate', function(event, result) {
            $scope.ledger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
