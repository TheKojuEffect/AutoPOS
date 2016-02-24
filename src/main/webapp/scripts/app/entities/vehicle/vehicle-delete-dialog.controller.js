'use strict';

angular.module('autopos')
	.controller('VehicleDeleteController', function($scope, $uibModalInstance, entity, Vehicle) {

        $scope.vehicle = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Vehicle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
