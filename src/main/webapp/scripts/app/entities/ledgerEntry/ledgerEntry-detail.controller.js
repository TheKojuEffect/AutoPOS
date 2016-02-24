'use strict';

angular.module('autoposApp')
    .controller('LedgerEntryDetailController', function ($scope, $rootScope, $stateParams, entity, LedgerEntry, Ledger) {
        $scope.ledgerEntry = entity;
        $scope.load = function (id) {
            LedgerEntry.get({id: id}, function(result) {
                $scope.ledgerEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:ledgerEntryUpdate', function(event, result) {
            $scope.ledgerEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
