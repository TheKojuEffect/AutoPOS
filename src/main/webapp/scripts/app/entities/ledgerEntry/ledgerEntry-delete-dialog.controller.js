'use strict';

angular.module('autoposApp')
	.controller('LedgerEntryDeleteController', function($scope, $uibModalInstance, entity, LedgerEntry) {

        $scope.ledgerEntry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LedgerEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
