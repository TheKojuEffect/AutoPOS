'use strict';

angular.module('autopos')
    .controller('LedgerDetailController', function ($scope, $rootScope, $stateParams, entity, Ledger, Customer) {
        $scope.ledger = entity;
        $scope.load = function (id) {
            Ledger.get({id: id}, function(result) {
                $scope.ledger = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:ledgerUpdate', function(event, result) {
            $scope.ledger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
