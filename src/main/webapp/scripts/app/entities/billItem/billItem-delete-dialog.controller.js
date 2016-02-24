'use strict';

angular.module('autopos')
	.controller('BillItemDeleteController', function($scope, $uibModalInstance, entity, BillItem) {

        $scope.billItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BillItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
