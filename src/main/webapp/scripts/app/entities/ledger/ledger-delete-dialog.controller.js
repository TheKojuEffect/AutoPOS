'use strict';

angular.module('autopos')
	.controller('LedgerDeleteController', function($scope, $uibModalInstance, entity, Ledger) {

        $scope.ledger = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ledger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
